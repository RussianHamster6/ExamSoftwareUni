package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.DataSetters;
import SqLiteDataHandlers.IDataSetters;
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

    public void initialize(){
        navigator = new Navigator();
        dataSetter = new DataSetters();
        dataGetter = new DataGetters();
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
                rs = dataGetter.getAllByCol("question","questionID",td.getEditor().getText(),c);
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
                rs = dataGetter.getAllByCol("user","userID",td.getEditor().getText(),c);

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
