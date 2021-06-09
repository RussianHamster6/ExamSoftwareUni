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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class QuestionCreate {

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
    }

    public void addTag(){
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

    public void submitQuestion() throws IOException {
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
        loader.setLocation(QuestionCreate.class.getResource("/fxml/questionView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));

    }

    public void clearTags(){
        tagText.setText("");
    }
}
