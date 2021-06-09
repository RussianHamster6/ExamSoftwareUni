package Controllers;

import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.IDataGetters;
import UserDetails.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;


public class HomePage {

    @FXML
    private TextField UID;

    private INavigator navigator;
    private IDataGetters dataGetter;

    public void initialize() {
        navigator = new Navigator();
        dataGetter = new DataGetters();

        UID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    UID.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    public void Login(ActionEvent event) throws IOException {
        Connection c = null;
        ResultSet rs = null;

        try {
            rs = dataGetter.getAllByCol("user","userID",UID.getText());
            if(rs.next()){
                loginSuccess(new User(rs.getInt("userID"),
                        rs.getString("firstName"),
                        rs.getString("surname"),
                        dataConversions.testConversions.intToBool(rs.getInt("isEmployee"))
                ));
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "The ID you entered could not be found");
                alert.showAndWait();
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private void loginSuccess(User userLogin) throws IOException {
        Stage stage = (Stage) UID.getScene().getWindow();

        if(userLogin.getIsEmployee()){
            Menu menu = new Menu();
            menu.setCurUser(userLogin);
            navigator.changeSceneWithClass(stage,"menu",menu);
        }
        else{
            AvailableTestsView ATV = new AvailableTestsView();
            ATV.setCurUser(userLogin);
            navigator.changeSceneWithClass(stage,"availableTestsView",ATV);
        }
    }

}
