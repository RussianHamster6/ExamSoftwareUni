package Controllers;

import QuestionPack.Question;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionView{

    @FXML
    public TableView<Question> QTable;
    @FXML
    public javafx.scene.control.TableColumn<Question,Integer> QIDCol;
    @FXML
    public javafx.scene.control.TableColumn<Question,String> DescCol;
    @FXML
    public javafx.scene.control.TableColumn<Question, String> QTypeCol;
    @FXML
    public javafx.scene.control.TableColumn<Question,String> AnsCol;
    @FXML
    public javafx.scene.control.TableColumn<Question,Integer> PointsCol;
    @FXML
    public javafx.scene.control.TableColumn<Question, ArrayList<String>> TagCol;

    public void initialize(){
        System.out.println("initialize called");
        try{
            QIDCol.setCellValueFactory(new PropertyValueFactory<>("QID"));
            DescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            QTypeCol.setCellValueFactory(new PropertyValueFactory<>("QType"));
            AnsCol.setCellValueFactory(new PropertyValueFactory<>("answer"));
            PointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
            TagCol.setCellValueFactory(new PropertyValueFactory<>("tagList"));

            Connection c = null;

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("Select * from question;");

            while (rs.next()){
                Question QToAdd = new Question(rs.getInt("questionID"),
                        rs.getString("description"),
                        rs.getString("questionType"),
                        rs.getString("answer"),
                        rs.getInt("points"));
                if (rs.getString("tags") != null){
                    ArrayList<String> tagArrayList = new ArrayList<String>(Arrays.asList(rs.getString("tags").split(",")));
                    QToAdd.tagList = (ArrayList<String>) tagArrayList;
                }
                QTable.getItems().add(QToAdd);
            }
            System.out.println("end");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
