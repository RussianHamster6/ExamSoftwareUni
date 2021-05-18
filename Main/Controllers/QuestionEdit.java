package Controllers;

import QuestionPack.Question;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class QuestionEdit {

    @FXML
    AnchorPane anchor;
    @FXML
    TextField descriptionText;
    @FXML
    TextField answerText;
    @FXML
    ComboBox qTypeBox;
    @FXML
    TextArea tagText;
    @FXML
    TextField pointText;

    private Question curQuestion;

    public void setLocalQuestion(Question question){
        this.curQuestion = question;
    }

    public Question getCurQuestion(){
        return this.curQuestion;
    }

    public void initialize(){
        qTypeBox.setItems(FXCollections.observableArrayList(Question.questionType.values()));

        pointText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pointText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        setData();
    }

    public void addTag(){

        setData();

        TextInputDialog td = new TextInputDialog("Type Here");
        td.setTitle("Enter your tag");
        td.setContentText("Enter your tag");
        td.showAndWait();

        if(tagText.getText().length() == 0){
            tagText.setText(td.getEditor().getText());
        }
        else {
            tagText.setText(tagText.getText() + "," + td.getEditor().getText());
        }

    }

    public void cloneQuestion() throws IOException {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("INSERT INTO question (description,questionType,answer,tags,points) VALUES(?, ?, ?, ?, ?);");
            statement.setString(1,descriptionText.getText());
            statement.setString(2,qTypeBox.getValue().toString());
            statement.setString(3,answerText.getText());
            statement.setString(4,tagText.getText());
            statement.setInt(5, Integer.parseInt(pointText.getText()));
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Should have pushed data");


        Stage stage = (Stage) answerText.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/questionView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));

    }

    public void updateQuestion() throws IOException {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("Update question SET description = ?," +
                    "questionType= ?," +
                    "answer= ?," +
                    "tags= ?," +
                    "points= ? WHERE questionID = ?");
            statement.setString(1,descriptionText.getText());
            statement.setString(2,qTypeBox.getValue().toString());
            statement.setString(3,answerText.getText());
            statement.setString(4,tagText.getText());
            statement.setInt(5, Integer.parseInt(pointText.getText()));
            statement.setInt(6,curQuestion.getQID());
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Should have pushed data");


        Stage stage = (Stage) answerText.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/questionView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

    private void setData(){
        Question question = getCurQuestion();
        descriptionText.setText(curQuestion.description);
        answerText.setText(curQuestion.answer);
        qTypeBox.setValue(curQuestion.getQType());
        String tags = curQuestion.tagList.toString().replaceAll("\\[", "").replaceAll("\\]","");
        tagText.setText(tags);
        pointText.setText(String.valueOf(curQuestion.getPoints()));
    }

    public void delete() throws IOException {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("DELETE FROM question WHERE questionID = ?");
            statement.setInt(1,curQuestion.getQID());
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        Stage stage = (Stage) answerText.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/questionView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

    public void clearTags(){
        tagText.setText("");
    }
}
