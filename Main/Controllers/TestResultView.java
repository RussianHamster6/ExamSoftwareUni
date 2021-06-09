package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.IDataGetters;
import TestPack.TestResult;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
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

    public void initialize(){

        searchText.textProperty().addListener((observable,oldvalue,newvalue) -> {
            searchTag(newvalue);
        });

        dataGetter = new DataGetters();

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

            ResultSet rs = dataGetter.getAll("TestResult;",c);

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
