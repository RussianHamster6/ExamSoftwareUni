package Controllers;

import UserDetails.Employee;
import UserDetails.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static java.lang.Integer.parseInt;


public class HomePage {

    @FXML
    private TextField UID;

    @FXML
    public void stuLogin(ActionEvent event){
        Student student = new Student("test","test", false, 123);

        if(student.stuNumber == parseInt(UID.getText())){
            System.out.println("I be Student");
        }
    }

    public void empLogin(ActionEvent event){
        Employee employee = new Employee("test","test", false, 123);

        if(employee.empNumber == parseInt(UID.getText())){
            //Change page to Employee view
            System.out.println("I be Staff");
        }
    }

    public void DBConnect(){
        Connection c = null;

        Employee employee = new Employee("test","test", false, 123);

        /* Select * for lateer
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("Select * from user;");

            while (rs.next()){
                System.out.println(rs.getString("firstName"));
            }
            System.out.println("end");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }*/

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("INSERT INTO user (firstName,surname,isEmployee) VALUES(?, ?, ?);");
            statement.setString(1,employee.firstName);
            statement.setString(2,employee.surname);
            statement.setInt(3,1);
            statement.execute();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Should have pushed data");



    }
}
