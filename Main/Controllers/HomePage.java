package Controllers;

import UserDetails.Employee;
import UserDetails.Student;
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

    @FXML
    public void stuLogin(ActionEvent event){
        Student student = new Student("test","test", false, 123);

        if(student.stuNumber == parseInt(UID.getText())){
            System.out.println("I be Student");
        }
    }

    public void empLogin(ActionEvent event){
        Employee employee = new Employee("test","test", false, 123);

        if(employee.empNumber == parseInt(UID.getText())){
            //Change page to Employee view
            System.out.println("I be Staff");
        }
    }

    public void DBConnect() throws IOException {
        Stage stage = (Stage) UID.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/questionView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));

    }

}
