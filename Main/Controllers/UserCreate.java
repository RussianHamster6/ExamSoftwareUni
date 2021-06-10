package Controllers;

import SqLiteDataHandlers.DataSetters;
import SqLiteDataHandlers.IDataSetters;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

public class UserCreate {

    @FXML
    TextField FNameText;
    @FXML
    TextField SNameText;
    @FXML
    CheckBox isEmpBox;

    private INavigator navigator;
    private IDataSetters dataSetter;

    public void initialize(){
        navigator = new Navigator();
        dataSetter = new DataSetters();
    }

    public void submitUser(){
        Boolean success = dataSetter.createNewEntry(
                "user",
                "firstName,surname,isEmployee",
                FNameText.getText(),
                SNameText.getText(),
                String.valueOf(dataConversions.testConversions.boolToInt(isEmpBox.isSelected()))
        );

        if(success) {
            Stage stage = (Stage) FNameText.getScene().getWindow();
            navigator.changeScene(stage, "userView");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again");
            alert.showAndWait();
        }
    }
}
