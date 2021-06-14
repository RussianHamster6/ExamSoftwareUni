package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.DataSetters;
import SqLiteDataHandlers.IDataSetters;
import TestPack.ITestHelper;
import TestPack.TestHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    private IDataSetters dataSetter;
    private SqLiteDataHandlers.IDataGetters dataGetter;
    private ITestHelper testHelper;
    private boolean isManuallyMarked;

    public void initialize(){
        navigator = new Navigator();
        dataSetter = new DataSetters();
        dataGetter = new DataGetters();
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

    public void submitTest() throws IOException {

        Boolean success = dataSetter.createNewEntry(
                "test",
                "questionList,isManuallyMarked,testName,stuList",
                QIDsText.getText(),
                String.valueOf(dataConversions.testConversions.boolToInt(isManMarkedBox.isSelected())),
                descriptionText.getText(),
                StuIDsText.getText()
                );

        if(success) {
            Stage stage = (Stage) QIDsText.getScene().getWindow();
            navigator.changeScene(stage, "testView");
        }

    }

    public void emptyQID(){
        QIDsText.setText("");
    }

    public void emptyStuID(){
        StuIDsText.setText("");
    }
}
