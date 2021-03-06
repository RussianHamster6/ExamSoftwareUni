import Controllers.HomePage;
import Controllers.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExamSoftwareUni extends Application {

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/HomePage.fxml"));
        Parent root = loader.load();
        HomePage homePage = (HomePage)loader.getController();
        primaryStage.setTitle("Exams Software");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}


