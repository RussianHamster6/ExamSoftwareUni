package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.IDataGetters;
import UserDetails.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void initialize(){
        dataGetter = new DataGetters();

        try{
            UIDCol.setCellValueFactory(new PropertyValueFactory<>("UID"));
            FNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            SNameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
            IsEmpCol.setCellValueFactory(new PropertyValueFactory<>("isEmployee"));

            ResultSet rs = dataGetter.getAll("user");

            while (rs.next()){
                User UToAdd = new User(rs.getInt("userID"),
                        rs.getString("firstName"),
                        rs.getString("surName"),
                        dataConversions.testConversions.intToBool(rs.getInt("isEmployee")));
                UTable.getItems().add(UToAdd);
            }
            if (this.UTable != null) {
                UList = UTable.getItems();
            }
            System.out.println("end");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void addUser() throws IOException {
        Stage stage = (Stage) UTable.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/UserCreate.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

    public void editUser(MouseEvent event) throws IOException{
        if (event.getClickCount() > 1) {

            Stage stage = (Stage) UTable.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getResource("/fxml/userEdit.fxml"));
            UserEdit userEdit = new UserEdit();
            userEdit.setLocalUser(UTable.getSelectionModel().getSelectedItem());
            loader.setController(userEdit);
            Parent root = loader.load();

            stage.setScene(new Scene(root));
        }

    }

    public void searchTag(String searchString) {
        /*
        FilteredList<User> QFiltered = new FilteredList<User>(QList);
        if (searchString != null) {
            Predicate<User> tag = i -> {
                AtomicReference<Boolean> flag = new AtomicReference<>(false);
                var tagList = i.getTagList();
                tagList.forEach(curString -> {
                    if (curString.toLowerCase().contains(searchString.toLowerCase())) {
                        flag.set(true);
                    }
                });
                return flag.get();
            };
            QFiltered.setPredicate(tag);
        }
        else {
            QTable.setItems(QList);
        }

        QTable.setItems(QFiltered);

         */
    }
}
