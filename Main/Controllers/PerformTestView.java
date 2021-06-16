package Controllers;

import QuestionPack.Question;
import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.DataSetters;
import SqLiteDataHandlers.IDataSetters;
import TestPack.Test;
import UserDetails.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static QuestionPack.Question.questionType.multiChoice;

public class PerformTestView {
    @FXML
    TabPane testTabPane;

    Test curTest;
    User curUser;
    ArrayList<Question> QList;

    private IDataSetters dataSetter;
    private SqLiteDataHandlers.IDataGetters dataGetters;
    private INavigator navigator;

    public User getCurUser() {
        return curUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    public Test getCurTest() {
        return curTest;
    }

    public void setCurTest(Test curTest) {
        this.curTest = curTest;
    }

    public void initialize(){
        dataGetters = new DataGetters();
        dataSetter = new DataSetters();
        navigator = new Navigator();
        //Get list of question IDs from Test
        ArrayList<Integer> QIDList = this.getCurTest().questionList;
        QList = new ArrayList<Question>();

        QIDList.forEach(Q -> {
            Connection c = null;

            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                c.setAutoCommit(false);
                System.out.println("DBConnected");

                ResultSet rs = dataGetters.getAllByCol("question","questionID",String.valueOf(Q),c);

                if (rs.next()){
                    Question newQuestion = new Question(rs.getInt("questionID"),
                            rs.getString("description"),
                            rs.getString("questionType"),
                            rs.getString("answer"),
                            rs.getInt("points"));
                    if (rs.getString("tags") != null){
                        ArrayList<String> tagArrayList = new ArrayList<String>(Arrays.asList(rs.getString("tags").split(",")));
                        newQuestion.tagList = (ArrayList<String>) tagArrayList;
                    }
                    QList.add(newQuestion);
                    loadQuestion(newQuestion);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Test Contains invalid questions");
                    alert.showAndWait();
                }

                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        });
    }

    public void loadQuestion(Question qToLoad) throws IOException {
        switch (qToLoad.getQType()){
            case aritmetic:
                Tab newTab = new Tab();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(PerformTestView.class.getResource("/fxml/arithmeticQuestionTab.fxml"));
                newTab.setContent(loader.load());
                newTab.setText("Question " + (testTabPane.getTabs().size() + 1));
                newTab.getContent().lookup("#correctAnswerCheckBox").setVisible(curUser.getIsEmployee());
                if(newTab.getContent().lookup("#correctAnswerCheckBox").isVisible()) {
                    try {
                        Connection c = null;
                        Class.forName("org.sqlite.JDBC");
                        c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                        c.setAutoCommit(false);

                        String statementStr = "Select * from answer Where TestID = " + String.valueOf(getCurTest().getTestID());
                        Statement statement = c.createStatement();
                        ResultSet answer = statement.executeQuery(statementStr);

                        while (answer.next()) {
                            if (answer.getInt("StudentID") == getCurUser().getUID() && answer.getInt("QuestionID") == qToLoad.getQID()) {
                                String answerGiven = answer.getString("Answer");
                                TextField answerField = (TextField) newTab.getContent().lookup("#answerField");
                                answerField.setText(answerGiven);
                            }
                        }
                        c.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                    testTabPane.getTabs().add(newTab);
                    Label qLabel = (Label) testTabPane.lookup("#questionLabel");
                    qLabel.setId("");
                    qLabel.setText(qToLoad.getDescription());
                    break;
                    case multiChoice:
                        Tab newTabMulti = new Tab();

                        FXMLLoader loaderMulti = new FXMLLoader();
                        loaderMulti.setLocation(PerformTestView.class.getResource("/fxml/multiChoiceQuestion.fxml"));
                        newTabMulti.setContent(loaderMulti.load());
                        newTabMulti.setText("Question " + (testTabPane.getTabs().size() + 1));
                        newTabMulti.getContent().lookup("#correctAnswerCheckBox").setVisible(curUser.getIsEmployee());
                        if (newTabMulti.getContent().lookup("#correctAnswerCheckBox").isVisible()) {
                            try {
                                Connection c = null;
                                Class.forName("org.sqlite.JDBC");
                                c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                                c.setAutoCommit(false);

                                String statementStr = "Select * from answer Where TestID = " + String.valueOf(getCurTest().getTestID());
                                Statement statement = c.createStatement();
                                ResultSet answer = statement.executeQuery(statementStr);

                                while (answer.next()) {
                                    if (answer.getInt("StudentID") == getCurUser().getUID() && answer.getInt("QuestionID") == qToLoad.getQID()) {
                                        String answerGiven = answer.getString("Answer");
                                        switch (answerGiven) {
                                            case "A":
                                                RadioButton buttonA = (RadioButton) newTabMulti.getContent().lookup("#A");
                                                buttonA.setSelected(true);
                                                break;
                                            case "B":
                                                RadioButton buttonB = (RadioButton) newTabMulti.getContent().lookup("#B");
                                                buttonB.setSelected(true);
                                                break;
                                            case "C":
                                                RadioButton buttonC = (RadioButton) newTabMulti.getContent().lookup("#C");
                                                buttonC.setSelected(true);
                                                break;
                                            case "D":
                                                RadioButton buttonD = (RadioButton) newTabMulti.getContent().lookup("#D");
                                                buttonD.setSelected(true);
                                                break;
                                        }
                                    }
                                }
                                c.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                testTabPane.getTabs().add(newTabMulti);
                Label qLabelMulti =(Label) testTabPane.lookup("#questionLabel");
                qLabelMulti.setId("");
                qLabelMulti.setText(qToLoad.getDescription());
                break;
            case essay:
                Tab newTabEssay = new Tab();
                FXMLLoader loaderEssay = new FXMLLoader();
                loaderEssay.setLocation(PerformTestView.class.getResource("/fxml/essayQuestionTab.fxml"));
                newTabEssay.setContent(loaderEssay.load());
                newTabEssay.setText("Question " + (testTabPane.getTabs().size() + 1));
                newTabEssay.getContent().lookup("#correctAnswerCheckBox").setVisible(curUser.getIsEmployee());
                if(newTabEssay.getContent().lookup("#correctAnswerCheckBox").isVisible()){
                    try {
                        Connection c = null;
                        Class.forName("org.sqlite.JDBC");
                        c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                        c.setAutoCommit(false);

                        String statementStr = "Select * from answer Where TestID = " + String.valueOf(getCurTest().getTestID());
                        Statement statement = c.createStatement();
                        ResultSet answer = statement.executeQuery(statementStr);

                        while(answer.next()){
                            if(answer.getInt("StudentID") == getCurUser().getUID() && answer.getInt("QuestionID") == qToLoad.getQID()){
                                String answerGiven = answer.getString("Answer");
                                TextArea essayAnswerField = (TextArea) newTabEssay.getContent().lookup("#essayAnswerField");
                                essayAnswerField.setText(answerGiven);
                            }
                        }
                        c.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                testTabPane.getTabs().add(newTabEssay);
                Label qLabelEssay =(Label) testTabPane.lookup("#questionLabel");
                qLabelEssay.setId("");
                qLabelEssay.setText(qToLoad.getDescription());
                break;
        }
    }

    public void endTest() throws IOException {
        var tabList = testTabPane.getTabs();
        AtomicInteger i = new AtomicInteger();
        i.set(0);
        AtomicInteger score = new AtomicInteger();
        score.set(0);

        if(!getCurTest().isManuallyMarked) {
            tabList.forEach(T -> {
                Question currentQ = QList.get(i.get());

                switch (currentQ.getQType()) {
                    case aritmetic:
                        var ansField = T.getContent().lookup("#answerField");
                        TextField ansFieldText = (TextField) ansField;
                        String givenAns = ansFieldText.getText().toLowerCase().trim();
                        String actualAns = QList.get(i.get()).getAnswer().toLowerCase().trim();
                        if (givenAns.equals(actualAns)) {
                            score.set(score.get() + QList.get(i.get()).getPoints());
                        }
                        i.getAndIncrement();
                        break;
                    case multiChoice:
                        var radioButtons = T.getContent().lookupAll(".radio-button").toArray();
                        Boolean flag = false;
                        for (int ind = 0; ind < radioButtons.length && flag == false; ind++) {
                            RadioButton rb = (RadioButton) radioButtons[ind];
                            if (rb.isSelected()) {
                                flag = true;
                                if (rb.getText().equals(QList.get(i.get()).getAnswer())) {
                                    score.set(score.get() + QList.get(i.get()).getPoints());
                                }
                            }
                        }
                        i.getAndIncrement();
                        break;
                }
            });
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your score was: " + score);
            alert.showAndWait();

            Connection c = null;
            PreparedStatement statement;

            Boolean success =
                    dataSetter.createNewEntry(
                            "testResult",
                            "testId,stuNumber,finalScore",
                            String.valueOf(curTest.getTestID())
                            ,String.valueOf(curUser.getUID())
                            ,String.valueOf(score.get())
                    );

            if (success) {
                Stage stage = (Stage) testTabPane.getScene().getWindow();
                AvailableTestsView ATV = new AvailableTestsView();
                ATV.setCurUser(curUser);
                navigator.changeSceneWithClass(stage,"availableTestsView",ATV);
            }
            else{
                Alert alertIssue = new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again");
                alertIssue.showAndWait();
            }
        }
        else {
            tabList.forEach(T -> {
                Question currentQ = QList.get(i.get());

                switch (currentQ.getQType()) {
                    case aritmetic:
                        var ansField = T.getContent().lookup("#answerField");
                        TextField ansFieldText = (TextField) ansField;
                        dataSetter.createNewEntry("Answer","TestID,StudentID,Answer,QuestionID" ,String.valueOf(getCurTest().testID),String.valueOf(curUser.getUID()),ansFieldText.getText(),String.valueOf(currentQ.getQID()));
                        i.getAndIncrement();
                        break;
                    case multiChoice:
                        var radioButtons = T.getContent().lookupAll(".radio-button").toArray();
                        Boolean flag = false;
                        for (int ind = 0; ind < radioButtons.length && flag == false; ind++) {
                            RadioButton rb = (RadioButton) radioButtons[ind];
                            if (rb.isSelected()) {
                                flag = true;

                                dataSetter.createNewEntry("Answer","TestID,StudentID,Answer,QuestionID" ,String.valueOf(getCurTest().testID),String.valueOf(curUser.getUID()),rb.getText(),String.valueOf(currentQ.getQID()));
                            }
                        }
                        i.getAndIncrement();
                        break;
                    case essay:
                        var lookup = T.getContent().lookup("#essayAnswerField");
                        TextArea essayAnsField = (TextArea) lookup;
                        dataSetter.createNewEntry("Answer","TestID,StudentID,Answer,QuestionID" ,String.valueOf(getCurTest().testID),String.valueOf(curUser.getUID()),essayAnsField.getText(),String.valueOf(currentQ.getQID()));
                        i.getAndIncrement();
                        break;
                }
            });

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "This test requires manual marking, please see your teacher for your grade");
            alert.showAndWait();

            Connection c = null;
            PreparedStatement statement;

            Boolean success =
                    dataSetter.createNewEntry(
                            "testResult",
                            "testId,stuNumber,finalScore",
                            String.valueOf(curTest.getTestID())
                            ,String.valueOf(curUser.getUID())
                            ,""
                    );

            if (success) {
                Stage stage = (Stage) testTabPane.getScene().getWindow();
                AvailableTestsView ATV = new AvailableTestsView();
                ATV.setCurUser(curUser);
                navigator.changeSceneWithClass(stage,"availableTestsView",ATV);
            }
            else{
                Alert alertIssue = new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again");
                alertIssue.showAndWait();
            }
        }


    }
}
