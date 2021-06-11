package dojo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.JishoModel;
import statistics.Statistics;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class DojoController implements Initializable {
    @FXML
    public Label level;
    @FXML
    private Label partsOfSpeech;
    @FXML
    private Label englishDefinition;
    @FXML
    private Label promptText;
    @FXML
    private Label reading;
    @FXML
    private Label word;

    private int promptTextShowed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(JishoModel.getJishoModelArrayList());
        promptTextShowed = 0;

    }

    public void goBackToStatisticsScreen(MouseEvent mouseEvent) throws IOException {
        Stage stage = Statistics.getMainAppStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../statistics/statistics.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }


    public void showKanasOnCanvas(MouseEvent mouseEvent) {
        switch (promptTextShowed){
            case 0:{
                promptText.setText(reading.getText());
                promptTextShowed++;
            } break;
            case 1: {
                promptText.setText(word.getText());
                promptTextShowed++;
            } break;
            case 2: {
                promptText.setText("");
                promptTextShowed = 0;
            } break;
        }


    }

    public void selectRandomWord(MouseEvent mouseEvent) {
        ArrayList<JishoModel> jishoModelArrayList = JishoModel.getJishoModelArrayList();
        Random rand = new Random();
        JishoModel model = jishoModelArrayList.get(rand.nextInt(jishoModelArrayList.size()));
        partsOfSpeech.setText(model.getPartsOfSpeech().toString());
        englishDefinition.setText(model.getEnglishDefinition().toString());
        word.setText(model.getWord());
        reading.setText(model.getReading());
        level.setText(model.getnLevel().toString());
    }
}
