package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.IDataGetters;
import TestPack.Test;
import UserDetails.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AvailableTestsView {

    public User curUser;
    public ObservableList<Test> TList;
    @FXML
    public TableView<Test> TTable;
    @FXML
    public javafx.scene.control.TableColumn<Test,String> testNameCol;

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    public User getCurUser() {
        return curUser;
    }

    private IDataGetters dataGetter;
    private INavigator navigator;

    public void initialize(){
        dataGetter = new DataGetters();
        navigator = new Navigator();
        try{
            testNameCol.setCellValueFactory(new PropertyValueFactory<>("testName"));
            Connection c = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(true);
            System.out.println("Opened database successfully");
            ResultSet rs = dataGetter.getAll("test",c);

            String curUserIDString = String.valueOf(this.getCurUser().getUID());

            while (rs.next()) {
                if (doesStuListContainUID(curUserIDString, rs.getString("stuList")) || curUser.isEmployee) {
                    Test TToAdd = new Test(rs.getInt("TestID"),
                            dataConversions.testConversions.intToBool(rs.getInt("isManuallyMarked")),
                            rs.getString("testName"));
                    if (!rs.getString("stuList").isEmpty() && rs.getString("stuList") != null) {
                        String[] strArr = (rs.getString("stuList").split(","));
                        ArrayList<Integer> stuArrayList = new ArrayList<Integer>();
                        for (int i = 0; i < strArr.length; i++) {
                            String num = strArr[i];
                            stuArrayList.add(Integer.parseInt(num));
                        }
                        TToAdd.stuList = (ArrayList<Integer>) stuArrayList;
                    }
                    if (!rs.getString("questionList").isEmpty() && rs.getString("questionList") != null) {
                        String[] strArr = (rs.getString("questionList").split(","));
                        ArrayList<Integer> quesArrayList = new ArrayList<Integer>();
                        for (int i = 0; i < strArr.length; i++) {
                            String num = strArr[i];
                            quesArrayList.add(Integer.parseInt(num));
                        }
                        TToAdd.questionList = (ArrayList<Integer>) quesArrayList;
                    }
                    TTable.getItems().add(TToAdd);
                }
            }
            c.close();
            TList = TTable.getItems();

            System.out.println("end");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    public boolean doesStuListContainUID(String stuString,String testStuString){
        boolean flag = false;
        String[] strArr = null;
        strArr = (testStuString.split(","));
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals(stuString) && !testStuString.isEmpty()){
                flag = true;
            }
        }
        return flag;
    }

    public void viewTest(MouseEvent event) throws IOException {
        if (event.getClickCount() > 1) {
            Stage stage = (Stage) TTable.getScene().getWindow();

            PerformTestView testView = new PerformTestView();
            testView.setCurTest(TTable.getSelectionModel().getSelectedItem());
            testView.setCurUser(this.getCurUser());
            navigator.changeSceneWithClass(stage,"performTestView",testView);
        }
    }
}
