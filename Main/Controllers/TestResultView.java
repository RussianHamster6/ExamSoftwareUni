package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.IDataGetters;
import javafx.scene.control.Alert;
import navigator.*;
import TestPack.Test;
import TestPack.TestResult;
import UserDetails.User;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class TestResultView {
    @FXML
    public TableView<TestResult> TRTable;
    @FXML
    public javafx.scene.control.TableColumn<TestResult,Integer> ResultIDCol;
    @FXML
    public javafx.scene.control.TableColumn<TestResult, Integer> StudentIDCol;
    @FXML
    public javafx.scene.control.TableColumn<TestResult, Integer> TestIDCol;
    @FXML
    public javafx.scene.control.TableColumn<TestResult,Integer> ScoreCol;
    @FXML
    public javafx.scene.control.TableColumn<TestResult, String> StuNameCol;
    @FXML
    public javafx.scene.control.TableColumn<TestResult, String> TestNameCol;
    @FXML
    public TextField searchText;

    private ObservableList<TestResult> TRList;

    private IDataGetters dataGetter;
    private INavigator navigator;

    public void initialize(){

        searchText.textProperty().addListener((observable,oldvalue,newvalue) -> {
            searchTag(newvalue);
        });

        dataGetter = new DataGetters();
        navigator = new Navigator();

        try {
            ResultIDCol.setCellValueFactory(new PropertyValueFactory<>("resultId"));
            StudentIDCol.setCellValueFactory(new PropertyValueFactory<>("stuNum"));
            TestIDCol.setCellValueFactory(new PropertyValueFactory<>("testId"));
            ScoreCol.setCellValueFactory(new PropertyValueFactory<>("finalScore"));
            StuNameCol.setCellValueFactory(new PropertyValueFactory<>("StuName"));
            TestNameCol.setCellValueFactory(new PropertyValueFactory<>("TestName"));

            Connection c = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(true);
            System.out.println("Opened database successfully");

            ResultSet rs = dataGetter.getAll("TestResult",c);

            while (rs.next()){
                TestResult TRToAdd = new TestResult(rs.getInt("resultId"),
                        rs.getInt("testID"),
                        rs.getInt("stuNumber"),
                        rs.getInt("finalScore"));
                ResultSet stuRS = dataGetter.getAllByCol("user","userID", String.valueOf(TRToAdd.getStuNum()),c);

                if(stuRS.next()){
                    TRToAdd.setStuName(stuRS.getString("firstName") + " " +stuRS.getString("surname"));
                }
                ResultSet testRS = dataGetter.getAllByCol("test","testID", String.valueOf(TRToAdd.getTestId()),c);

                if(testRS.next()){
                    TRToAdd.setTestName(testRS.getString("testName"));
                }
                TRTable.getItems().add(TRToAdd);
            }
            TRList =(ObservableList<TestResult>) TRTable.getItems();
            c.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void markQuestion(MouseEvent event) throws SQLException {
        if (event.getClickCount() > 1 && TRTable.getSelectionModel().getSelectedItem().getFinalScore() == 0) {
            Stage stage = (Stage) TRTable.getScene().getWindow();
            PerformTestView testView = new PerformTestView();
            int TestId = TRTable.getSelectionModel().getSelectedItem().getTestId();
            try {
                Connection c = null;
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                c.setAutoCommit(true);

                ResultSet result = dataGetter.getAllByCol("test", "testID", String.valueOf(TestId), c);
                if (result.next()) {
                    Test test = new Test(result.getInt("TestID"),
                            dataConversions.testConversions.intToBool(result.getInt("isManuallyMarked")),
                            result.getString("testName"));
                    if (!result.getString("stuList").isEmpty() && result.getString("stuList") != null) {
                        String[] questionList = (result.getString("questionList").split(","));
                        ArrayList<Integer> questionArrayList = new ArrayList<Integer>();
                        for (int i = 0; i < questionList.length; i++) {
                            String num = questionList[i];
                            questionArrayList.add(Integer.parseInt(num));
                        }
                        test.questionList = (ArrayList<Integer>) questionArrayList;
                    }
                    testView.setCurTest(test);
                    testView.setCurUser(new User(TRTable.getSelectionModel().getSelectedItem().stuNum, "staff", "user", true));
                    navigator.changeSceneWithClass(stage, "performTestView", testView);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                    alert.showAndWait();
                }
                c.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void searchTag(String searchString) {

        FilteredList<TestResult> TFiltered = new FilteredList<TestResult>(TRList);
        if (searchString != null) {
            Predicate<TestResult> tag = i -> i.getTestName().contains(searchString);

            TFiltered.setPredicate(tag);
        }
        else {
            TRTable.setItems(TRList);
        }

        TRTable.setItems(TFiltered);

    }
}
