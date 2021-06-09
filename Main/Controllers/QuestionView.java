package Controllers;

import QuestionPack.Question;
import SqLiteDataHandlers.DataGetters;
import SqLiteDataHandlers.IDataGetters;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import navigator.INavigator;
import navigator.Navigator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

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
    @FXML
    public TextField searchText;
    public ObservableList<Question> QList;

    private IDataGetters dataGetter;
    private INavigator navigator;

    public void initialize() throws SQLException {

        dataGetter = new DataGetters();
        navigator = new Navigator();

        searchText.textProperty().addListener((observable,oldvalue,newvalue) -> {
            searchTag(newvalue);
        });

        //Set cell values on the question table.
            QIDCol.setCellValueFactory(new PropertyValueFactory<>("QID"));
            DescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            QTypeCol.setCellValueFactory(new PropertyValueFactory<>("QType"));
            AnsCol.setCellValueFactory(new PropertyValueFactory<>("answer"));
            PointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
            TagCol.setCellValueFactory(new PropertyValueFactory<>("tagList"));
            ResultSet rs = null;
            Connection c = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                c.setAutoCommit(true);
                System.out.println("Opened database successfully");
                rs = dataGetter.getAll("question",c);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        if (rs != null) {
            try {
                while (rs.next()) {
                    Question QToAdd = new Question(rs.getInt("questionID"),
                            rs.getString("description"),
                            rs.getString("questionType"),
                            rs.getString("answer"),
                            rs.getInt("points"));
                    if (rs.getString("tags") != null) {
                        ArrayList<String> tagArrayList = new ArrayList<String>(Arrays.asList(rs.getString("tags").split(",")));
                        QToAdd.tagList = (ArrayList<String>) tagArrayList;
                    }
                    QTable.getItems().add(QToAdd);
                }
                if (this.QTable != null) {
                    QList = QTable.getItems();
                }
                System.out.println("end");

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

        }
        c.close();
    }

    public void addQuestion() throws IOException {
        Stage stage = (Stage) searchText.getScene().getWindow();

        navigator.changeScene(stage, "questionCreate");
    }

    public void editQuestion(MouseEvent event) throws IOException{
        if (event.getClickCount() > 1) {

            Stage stage = (Stage) QTable.getScene().getWindow();
            QuestionEdit questionEdit = new QuestionEdit();
            questionEdit.setLocalQuestion(QTable.getSelectionModel().getSelectedItem());
            navigator.changeSceneWithClass(stage,"questionEdit",questionEdit);
        }
    }

    public void searchTag(String searchString) {
        FilteredList<Question> QFiltered = new FilteredList<Question>(QList);
        if (searchString != null) {
            Predicate<Question> tag = i -> {
                AtomicReference<Boolean> flag = new AtomicReference<>(false);
                var tagList = i.getTagList();
                tagList.forEach(curString -> {
                    if (curString.toLowerCase().contains(searchString.toLowerCase())) {
                        flag.set(true);
                    }
                });
                return flag.get();
            };
            QFiltered.setPredicate(tag);
        }
        else {
            QTable.setItems(QList);
        }

        QTable.setItems(QFiltered);
    }
}
