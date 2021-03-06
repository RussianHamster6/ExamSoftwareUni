package Controllers;

import UserDetails.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class HomePage {

    @FXML
    private TextField UID;

    public void initialize() {
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
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("Select * FROM user WHERE userID = ?");
            statement.setInt(1, Integer.parseInt(UID.getText()));
            statement.execute();
            rs = statement.getResultSet();
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
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private void loginSuccess(User userLogin) throws IOException {
        Stage stage = (Stage) UID.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        if(userLogin.getIsEmployee()){
            loader.setLocation(SceneController.class.getResource("/fxml/menu.fxml"));
            Menu menu = new Menu();
            menu.setCurUser(userLogin);
            loader.setController(menu);
        }
        else{
            loader.setLocation(SceneController.class.getResource("/fxml/availableTestsView.fxml"));
            AvailableTestsView ATV = new AvailableTestsView();
            ATV.setCurUser(userLogin);
            loader.setController(ATV);
        }

        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

}
