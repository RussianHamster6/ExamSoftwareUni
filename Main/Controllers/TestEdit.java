package Controllers;

import TestPack.Test;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class TestEdit {

    @FXML
    TextField descriptionText;
    @FXML
    TextArea QIDsText;
    @FXML
    CheckBox isManMarkedBox;
    @FXML
    TextArea StuIDsText;

    private Test curTest;

    public void setLocalQuestion(Test test){
        this.curTest = test;
    }

    public Test getCurTest(){
        return this.curTest;
    }

    public void initialize(){
        setData();
    }

    public void addQuestion() throws IOException {
        TextInputDialog td = new TextInputDialog("Type Here");
        td.setTitle("Enter your QuestionID");
        td.setContentText("Enter your QuestionID");
        td.showAndWait();

        ResultSet rs = null;
        Connection c = null;

        if(dataConversions.testConversions.isInteger(td.getEditor().getText())) {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                c.setAutoCommit(false);
                System.out.println("DBConnected");
                PreparedStatement statement = c.prepareStatement("Select * from question where questionID = ?;");
                statement.setInt(1, Integer.parseInt(td.getEditor().getText()));
                statement.execute();
                rs = statement.getResultSet();
                c.commit();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            try {
                if (rs.next() != false) {
                    if (QIDsText.getText().length() == 0) {
                        QIDsText.setText(td.getEditor().getText());
                    } else {
                        QIDsText.setText(QIDsText.getText() + "," + td.getEditor().getText());
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The QID you entered could not be found");
                    alert.showAndWait();
                }

            } catch (SQLException throwables) {
                Alert alert = new Alert(Alert.AlertType.ERROR, throwables.getMessage());
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The QID you entered is not an integer");
            alert.showAndWait();
        }
    }

    public void addStudent() throws IOException {
        TextInputDialog td = new TextInputDialog("Type Here");
        td.setTitle("Enter your QuestionID");
        td.setContentText("Enter your QuestionID");
        td.showAndWait();

        ResultSet rs = null;
        Connection c = null;
        PreparedStatement statement = null;

        if(dataConversions.testConversions.isInteger(td.getEditor().getText())) {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                c.setAutoCommit(false);
                System.out.println("DBConnected");
                statement = c.prepareStatement("Select * from user where userID = ?;");
                statement.setInt(1, Integer.parseInt(td.getEditor().getText()));
                statement.execute();
                rs = statement.getResultSet();

                if (rs.next() != false ) {
                    if(rs.getInt("isEmployee") == 0) {
                        if (StuIDsText.getText().length() == 0) {
                            StuIDsText.setText(td.getEditor().getText());
                        } else {
                            StuIDsText.setText(StuIDsText.getText() + "," + td.getEditor().getText());
                        }
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR, "The QID you entered is for a Staff member");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The QID you entered could not be found");
                    alert.showAndWait();
                }

                c.commit();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The QID you entered is not an integer");
            alert.showAndWait();
        }
    }

    public void cloneTest() throws IOException {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("INSERT INTO Test (questionList,isManuallyMarked,testName,stuList) VALUES(?, ?, ?, ?);");
            statement.setString(1,QIDsText.getText().replaceAll("\\]",""));
            statement.setInt(2,dataConversions.testConversions.boolToInt(isManMarkedBox.isSelected()));
            statement.setString(3,descriptionText.getText());
            statement.setString(4, StuIDsText.getText().replaceAll("\\]",""));
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Should have pushed data");

        Stage stage = (Stage) QIDsText.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/testView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

    public void updateTest() throws IOException {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("Update Test SET " +
                    "questionList= ?," +
                    "isManuallyMarked= ?," +
                    "testName= ?," +
                    "stuList= ? WHERE testID = ?");
            statement.setString(1,QIDsText.getText().replaceAll(" ", ""));
            statement.setInt(2,dataConversions.testConversions.boolToInt(isManMarkedBox.isSelected()));
            statement.setString(3,descriptionText.getText());
            statement.setString(4, StuIDsText.getText().replaceAll(" ", ""));
            statement.setInt(5,curTest.getTestID());
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Should have pushed data");


        Stage stage = (Stage) QIDsText.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/testView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

    private void setData(){
        Test test = getCurTest();
        descriptionText.setText(test.testName);
        isManMarkedBox.setSelected(test.isManuallyMarked);
        String questions = test.questionList.toString().replaceAll("\\[", "").replaceAll("\\]","");
        QIDsText.setText(questions);
        String students = test.stuList.toString().replaceAll("\\[", "").replaceAll("\\]","");
        StuIDsText.setText(students);
    }

    public void delete() throws IOException {

        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("DELETE FROM test WHERE testID = ?");
            statement.setInt(1,curTest.getTestID());
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        Stage stage = (Stage) QIDsText.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/testView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

    public void emptyQID(){
        QIDsText.setText("");
    }

    public void emptyStuID(){
        StuIDsText.setText("");
    }
}
