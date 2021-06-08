package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.IDataGetters;
import TestPack.Test;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.function.Predicate;

public class TestView {

    @FXML
    public TableView<Test> TTable;
    @FXML
    public javafx.scene.control.TableColumn<Test,Integer> TestIDCol;
    @FXML
    public javafx.scene.control.TableColumn<Test,ArrayList<Integer>> questionListCol;
    @FXML
    public javafx.scene.control.TableColumn<Test, Boolean> isManuallyMarkedCol;
    @FXML
    public javafx.scene.control.TableColumn<Test,String> testNameCol;
    @FXML
    public javafx.scene.control.TableColumn<Test, ArrayList<Integer>> studentListCol;
    @FXML
    public TextField searchText;
    public ObservableList<Test> TList;
    private IDataGetters dataGetter;

    public void initialize(){

        dataGetter = new DataGetters();

        searchText.textProperty().addListener((observable,oldvalue,newvalue) -> {
            searchTag(newvalue);
        });

        try{
            TestIDCol.setCellValueFactory(new PropertyValueFactory<>("testID"));
            questionListCol.setCellValueFactory(new PropertyValueFactory<>("questionList"));
            isManuallyMarkedCol.setCellValueFactory(new PropertyValueFactory<>("isManuallyMarked"));
            testNameCol.setCellValueFactory(new PropertyValueFactory<>("testName"));
            studentListCol.setCellValueFactory(new PropertyValueFactory<>("stuList"));

            ResultSet rs = dataGetter.getAll("test");


            while (rs.next()){
                Test TToAdd = new Test(rs.getInt("TestID"),
                        dataConversions.testConversions.intToBool(rs.getInt("isManuallyMarked")) ,
                        rs.getString("testName"));
                if (!rs.getString("stuList").isEmpty() && rs.getString("stuList") != null){
                    String[] strArr = (rs.getString("stuList").split(","));
                    ArrayList<Integer> stuArrayList = new ArrayList<Integer>();
                    for (int i = 0; i < strArr.length; i++) {
                        String num = strArr[i];
                        stuArrayList.add(Integer.parseInt(num)) ;
                    }
                    TToAdd.stuList = (ArrayList<Integer>) stuArrayList;
                }
                if (!rs.getString("questionList").isEmpty() && rs.getString("questionList") != null){
                    String[] strArr = (rs.getString("questionList").split(","));
                    ArrayList<Integer> quesArrayList = new ArrayList<Integer>();
                    for (int i = 0; i < strArr.length; i++) {
                        String num = strArr[i];
                        quesArrayList.add(Integer.parseInt(num)) ;
                    }
                    TToAdd.questionList = (ArrayList<Integer>) quesArrayList;
                }
                TTable.getItems().add(TToAdd);
            }
            TList = TTable.getItems();

            System.out.println("end");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void addQuestion() throws IOException {
        Stage stage = (Stage) searchText.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/testCreate.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

    public void editQuestion(MouseEvent event) throws IOException{
        if (event.getClickCount() > 1) {

            Stage stage = (Stage) TTable.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getResource("/fxml/testEdit.fxml"));
            TestEdit testEdit = new TestEdit();
            testEdit.setLocalQuestion(TTable.getSelectionModel().getSelectedItem());
            loader.setController(testEdit);
            Parent root = loader.load();

            stage.setScene(new Scene(root));

        }
    }

    public void searchTag(String searchString) {

        FilteredList<Test> TFiltered = new FilteredList<Test>(TList);
        if (searchString != null) {
            Predicate<Test> tag = i -> i.testName.contains(searchString);

            TFiltered.setPredicate(tag);
        }
        else {
            TTable.setItems(TList);
        }

        TTable.setItems(TFiltered);

    }

}
