package Controllers;

import UserDetails.User;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserEdit {

    @FXML
    TextField FNameText;
    @FXML
    TextField SNameText;
    @FXML
    CheckBox isEmpBox;

    private User curUser;
    private INavigator navigator;

    public void setLocalUser(User user){
        this.curUser = user;
    }

    public User getCurTest(){
        return this.curUser;
    }

    public void initialize(){
        setData();
        navigator = new Navigator();
    }

    public void cloneUser() throws IOException {
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
        navigator.changeScene(stage,"userView");
    }

    private void setData(){
        User user = getCurTest();
        FNameText.setText(user.firstName);
        SNameText.setText(user.surname);
        isEmpBox.setSelected(user.getIsEmployee());
    }

    public void duplicateUser() throws IOException {
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
        navigator.changeScene(stage,"userView");
    }

    public void updateUser() throws IOException {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("Update user SET firstName = ?," +
                    "surname= ?," +
                    "isEmployee= ? WHERE userID = ?");
            statement.setString(1,FNameText.getText());
            statement.setString(2,SNameText.getText());
            statement.setInt(3,dataConversions.testConversions.boolToInt(isEmpBox.isSelected()));
            statement.setInt(4,this.curUser.getUID());
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Should have pushed data");


        Stage stage = (Stage) FNameText.getScene().getWindow();
        navigator.changeScene(stage,"userView");
    }

    public void delete() throws IOException {

        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("DELETE FROM user WHERE userID = ?");
            statement.setInt(1,curUser.getUID());
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        Stage stage = (Stage) FNameText.getScene().getWindow();
        navigator.changeScene(stage,"userView");
    }
}
