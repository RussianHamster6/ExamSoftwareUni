package Controllers;

import UserDetails.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.io.IOException;

public class Menu {

    @FXML
    private Text nameText;
    @FXML
    private AnchorPane anchor;

    public User curUser;

    private INavigator navigator;

    public void initialize(){
        nameText.setText("Welcome, " + curUser.getName());

        navigator = new Navigator();
    }

    public void questionManagement(){
        navigator.popUp("QuestionView");
    }

    public void testManagement() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/testView.fxml"));
        Parent root = loader.load();

        stage.setTitle("Test Management");
        stage.setScene(new Scene(root));
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }

    public void userManagement() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/userView.fxml"));
        Parent root = loader.load();

        stage.setTitle("User Management");
        stage.setScene(new Scene(root));
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }

    public void testResultView() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/testResultView.fxml"));
        Parent root = loader.load();

        stage.setTitle("View Test Results");
        stage.setScene(new Scene(root));
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    public User getCurUser() {
        return curUser;
    }
}
