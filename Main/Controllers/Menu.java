package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {
    public void questionManagement() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/QuestionView.fxml"));
        Parent root = loader.load();

        stage.setTitle("Question Management");
        stage.setScene(new Scene(root));
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }
}
