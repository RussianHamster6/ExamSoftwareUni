package Controllers;

import TestPack.ITestHelper;
import TestPack.Test;
import TestPack.TestHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

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
    private INavigator navigator;
    private ITestHelper testHelper;

    public void setLocalQuestion(Test test){
        this.curTest = test;
    }

    public Test getCurTest(){
        return this.curTest;
    }

    public void initialize(){
        setData();
        navigator = new Navigator();
        testHelper = new TestHelper();
    }

    public void addQuestion() throws IOException {
        TextInputDialog td = new TextInputDialog("Type Here");
        td.setTitle("Enter your QuestionID");
        td.setContentText("Enter your QuestionID");
        td.showAndWait();

        QIDsText.setText(testHelper.returnQListAfterAdd(td.getEditor().getText(),isManMarkedBox,QIDsText.getText()));
    }

    public void addStudent() throws IOException {
        TextInputDialog td = new TextInputDialog("Type Here");
        td.setTitle("Enter your QuestionID");
        td.setContentText("Enter your QuestionID");
        td.showAndWait();

        StuIDsText.setText(testHelper.returnStuListAfterAdd(StuIDsText.getText(),td.getEditor().getText()));

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
        navigator.changeScene(stage,"testView");
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
        navigator.changeScene(stage,"testView");
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
        navigator.changeScene(stage,"testView");
    }

    public void emptyQID(){
        QIDsText.setText("");
    }

    public void emptyStuID(){
        StuIDsText.setText("");
    }
}
