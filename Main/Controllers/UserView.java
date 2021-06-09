package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.IDataGetters;
import UserDetails.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class UserView {

    @FXML
    public TableView<User> UTable;
    @FXML
    public javafx.scene.control.TableColumn<User,Integer> UIDCol;
    @FXML
    public javafx.scene.control.TableColumn<User,String> FNameCol;
    @FXML
    public javafx.scene.control.TableColumn<User, String> SNameCol;
    @FXML
    public javafx.scene.control.TableColumn<User,String> IsEmpCol;
    public ObservableList<User> UList;
    private IDataGetters dataGetter;
    private INavigator navigator;

    public void initialize(){
        dataGetter = new DataGetters();
        navigator = new Navigator();
        try{
            UIDCol.setCellValueFactory(new PropertyValueFactory<>("UID"));
            FNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            SNameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
            IsEmpCol.setCellValueFactory(new PropertyValueFactory<>("isEmployee"));
            Connection c = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(true);
            System.out.println("Opened database successfully");
            ResultSet rs = dataGetter.getAll("user",c);

            while (rs.next()){
                User UToAdd = new User(rs.getInt("userID"),
                        rs.getString("firstName"),
                        rs.getString("surName"),
                        dataConversions.testConversions.intToBool(rs.getInt("isEmployee")));
                UTable.getItems().add(UToAdd);
            }
            c.close();
            if (this.UTable != null) {
                UList = UTable.getItems();
            }
            System.out.println("end");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void addUser() {
        Stage stage = (Stage) UTable.getScene().getWindow();
        navigator.changeScene(stage,"UserCreate");
    }

    public void editUser(MouseEvent event){
        if (event.getClickCount() > 1) {
            Stage stage = (Stage) UTable.getScene().getWindow();
            UserEdit userEdit = new UserEdit();
            userEdit.setLocalUser(UTable.getSelectionModel().getSelectedItem());

            navigator.changeSceneWithClass(stage,"userEdit",userEdit);
        }

    }
}
