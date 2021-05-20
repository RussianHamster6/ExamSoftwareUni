package Controllers;

import QuestionPack.Question;
import TestPack.Test;
import UserDetails.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class PerformTestView {
    @FXML
    TabPane testTabPane;

    Test curTest;
    User curUser;
    ArrayList<Question> QList;

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
                PreparedStatement statement = c.prepareStatement("Select * from question Where questionID = ?;");
                statement.setInt(1,Q);
                statement.execute();

                ResultSet rs = statement.getResultSet();

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

                c.commit();
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
                loader.setLocation(SceneController.class.getResource("/fxml/arithmeticQuestionTab.fxml"));
                newTab.setContent(loader.load());
                newTab.setText("Question " + (testTabPane.getTabs().size() + 1));
                testTabPane.getTabs().add(newTab);
                Label qLabel =(Label) testTabPane.lookup("#questionLabel");
                qLabel.setId("");
                qLabel.setText(qToLoad.getDescription());
                break;
            case multiChoice:
                Tab newTabMulti = new Tab();

                FXMLLoader loaderMulti = new FXMLLoader();
                loaderMulti.setLocation(SceneController.class.getResource("/fxml/multiChoiceQuestion.fxml"));
                newTabMulti.setContent(loaderMulti.load());
                newTabMulti.setText("Question " + (testTabPane.getTabs().size() + 1));
                testTabPane.getTabs().add(newTabMulti);
                Label qLabelMulti =(Label) testTabPane.lookup("#questionLabel");
                qLabelMulti.setId("");
                qLabelMulti.setText(qToLoad.getDescription());
                break;
        }
    }
}
