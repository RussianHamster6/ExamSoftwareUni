package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    Stage priStage;

    public SceneController(Stage primaryStage){
        priStage = primaryStage;
    }

    public void loadScene(String pageName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/"+pageName+".fxml"));
        Parent root = loader.load();

        priStage.setTitle("Exams Software");
        priStage.setScene(new Scene(root));

        priStage.show();
    }
}
