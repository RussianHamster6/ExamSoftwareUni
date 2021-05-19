package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserCreate {

    @FXML
    TextField FNameText;
    @FXML
    TextField SNameText;
    @FXML
    CheckBox isEmpBox;

    public void initialize(){
    }


    public void submitUser() throws IOException {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("INSERT INTO user (firstName,surname,isEmployee) VALUES(?, ?, ?);");
            statement.setString(1,FNameText.getText());
            statement.setString(2,SNameText.getText());
            statement.setInt(3,dataConversions.testConversions.boolToInt(isEmpBox.isSelected()));
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Should have pushed data");

        Stage stage = (Stage) FNameText.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/userView.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));

    }
}
