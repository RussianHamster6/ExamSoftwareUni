package Controllers;

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

    public void initialize(){

        searchText.textProperty().addListener((observable,oldvalue,newvalue) -> {
            searchTag(newvalue);
        });

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
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("Select * from TestResult;");

            while (rs.next()){
                TestResult TRToAdd = new TestResult(rs.getInt("resultId"),
                        rs.getInt("testID"),
                        rs.getInt("stuNumber"),
                        rs.getInt("finalScore"));

                PreparedStatement preparedStatement = c.prepareStatement("select * from user where userID = ?");
                preparedStatement.setInt(1, TRToAdd.getStuNum());
                preparedStatement.execute();
                ResultSet stuRS = preparedStatement.getResultSet();

                if(stuRS.next()){
                    TRToAdd.setStuName(stuRS.getString("firstName") + " " +stuRS.getString("surname"));
                }

                PreparedStatement preparedStatement1 = c.prepareStatement("select * from test where testID = ?");
                preparedStatement1.setInt(1, TRToAdd.getTestId());
                preparedStatement1.execute();
                ResultSet testRS = preparedStatement1.getResultSet();

                if(testRS.next()){
                    TRToAdd.setTestName(testRS.getString("testName"));
                }
                TRTable.getItems().add(TRToAdd);
            }
            TRList =(ObservableList<TestResult>) TRTable.getItems();
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
