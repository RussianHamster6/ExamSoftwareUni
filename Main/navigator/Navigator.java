package navigator;

import Controllers.HomePage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator implements INavigator{

    /**
     * Makes a new window appear based on the appropriate stageToChange Variable
     *
     * @Param curStage the stage that is currently active
     * @Param stageToChange the string
     */
    public void popUp(String stageToChange) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Navigator.class.getResource("/fxml/"+ stageToChange +".fxml"));
            Parent root = loader.load();

            stage.setTitle(stageToChange);
            stage.setScene(new Scene(root));
            stage.setX(0);
            stage.setY(0);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void changeScene(Stage curStage, String stageToChange) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomePage.class.getResource("/fxml/"+ stageToChange +".fxml"));
            Parent root = loader.load();

            curStage.setScene(new Scene(root));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Needs implementing later, need to do a load while passing an object to the new controller
    public void changeSceneWithClass(Stage curStage, String stageToChange, Object objPassed) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomePage.class.getResource("/fxml/"+ stageToChange +".fxml"));
            loader.setController(objPassed);
            Parent root = loader.load();

            curStage.setScene(new Scene(root));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
