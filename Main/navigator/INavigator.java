package navigator;

import javafx.stage.Stage;

public interface INavigator {

    public void popUp(String stageToChange);

    public void changeScene(Stage curStage, String stageToChange);

    public void changeSceneWithClass(Stage curStage, String stageToChange, Object objPassed);
}
