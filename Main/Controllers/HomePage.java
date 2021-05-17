package Controllers;

import UserDetails.Employee;
import UserDetails.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Integer.parseInt;


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
        Student student = new Student("test","test", false, 123);
        Employee employee = new Employee("test","test", true, 1234);

        if(student.stuNumber == parseInt(UID.getText()) || employee.empNumber == parseInt(UID.getText())){
            loginSuccess();
        }
    }

    private void loginSuccess() throws IOException {
        Stage stage = (Stage) UID.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/menu.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

}
