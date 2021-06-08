package Controllers;

import UserDetails.User;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import navigator.INavigator;
import navigator.Navigator;

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

    public void testManagement() {
        navigator.popUp("testView");
    }

    public void userManagement(){
        navigator.popUp("userView");
    }

    public void testResultView() {
        navigator.popUp("testResultView");
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    public User getCurUser() {
        return curUser;
    }
}
