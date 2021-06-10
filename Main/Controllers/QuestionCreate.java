package Controllers;

import QuestionPack.Question;
import SqLiteDataHandlers.DataSetters;
import SqLiteDataHandlers.IDataSetters;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class QuestionCreate {

    @FXML
    TextField descriptionText;
    @FXML
    TextField answerText;
    @FXML
    ComboBox qTypeBox;
    @FXML
    TextArea tagText;
    @FXML
    TextField pointText;

    public IDataSetters dataSetter;

    public void initialize(){
        qTypeBox.setItems(FXCollections.observableArrayList(Question.questionType.values()));
        dataSetter = new DataSetters();

        pointText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pointText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void addTag(){
        TextInputDialog td = new TextInputDialog("Type Here");
        td.setTitle("Enter your tag");
        td.setContentText("Enter your tag");
        td.showAndWait();

        if(tagText.getText().length() == 0){
            tagText.setText(td.getEditor().getText());
        }
        else {
            tagText.setText(tagText.getText() + "," + td.getEditor().getText());
        }

    }

    public void submitQuestion() throws IOException {

        Boolean success = dataSetter.createNewEntry("question",
                "description,questionType,answer,tags,points",
                descriptionText.getText(),
                qTypeBox.getValue().toString(),
                answerText.getText(),
                tagText.getText(),
                pointText.getText());

        if(success) {

            Stage stage = (Stage) answerText.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuestionCreate.class.getResource("/fxml/questionView.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again");
            alert.showAndWait();
        }

    }

    public void clearTags(){
        tagText.setText("");
    }
}
