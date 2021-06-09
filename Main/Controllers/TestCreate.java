package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.io.IOException;
import java.sql.*;

public class TestCreate {

    @FXML
    TextField descriptionText;
    @FXML
    TextArea QIDsText;
    @FXML
    CheckBox isManMarkedBox;
    @FXML
    TextArea StuIDsText;

    private INavigator navigator;

    public void initialize(){
        navigator = new Navigator();
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
                System.exit(0);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The QID you entered is not an integer");
            alert.showAndWait();
        }
    }

    public void submitTest() throws IOException {

        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("INSERT INTO Test (questionList,isManuallyMarked,testName,stuList) VALUES(?, ?, ?, ?);");
            statement.setString(1,QIDsText.getText());
            statement.setInt(2,dataConversions.testConversions.boolToInt(isManMarkedBox.isSelected()));
            statement.setString(3,descriptionText.getText());
            statement.setString(4, StuIDsText.getText());
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Should have pushed data");

        Stage stage = (Stage) QIDsText.getScene().getWindow();
        navigator.changeScene(stage,"testView");

    }

    public void emptyQID(){
        QIDsText.setText("");
    }

    public void emptyStuID(){
        StuIDsText.setText("");
    }
}
