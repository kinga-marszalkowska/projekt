package dojo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.JishoResponseModel;
import models.Pen;
import statistics.Statistics;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class DojoController implements Initializable {
    @FXML
    public Label level;
    @FXML
    public ComboBox levelComboBox;
    public VBox topVBox;
    public VBox bottomVbox;
    @FXML
    private Slider fontSizeSlider;
    @FXML
    private Canvas dojoCanvas;
    @FXML
    private Label promptText;
    @FXML
    private Label reading;
    @FXML
    private Label word;

    private int promptTextShowed;
    private GraphicsContext dojoGraphicsContext;
    private boolean isTranslateSelected = false;
    private JishoResponseModel currentModel;
    private final ObservableList<String> LEVELS = FXCollections.observableArrayList(Arrays.asList("n1", "n2", "n3", "n4", "n5"));


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dojoGraphicsContext = dojoCanvas.getGraphicsContext2D();
        setPen();
        promptTextShowed = 0;
        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> promptText.setFont(new Font(fontSizeSlider.getValue())));
        levelComboBox.getItems().addAll(LEVELS);

        for (VBox vBox : new VBox[]{topVBox, bottomVbox}) {
            vBox.setBackground(
                    new Background(
                            new BackgroundFill(Color.rgb(250, 250, 250, 0.8),
                                    new CornerRadii(15),
                                    new Insets(5,10,5,10))));
        }

    }

    public void showKanasOnCanvas(MouseEvent mouseEvent) {
        if(currentModel != null){
            switch (promptTextShowed){
                case 0:{
                    promptText.setText("");
                    promptTextShowed++;
                } break;
                case 1: {
                    promptText.setText(currentModel.getReading());
                    promptTextShowed++;
                } break;
                case 2: {
                    promptText.setText(currentModel.getWord());
                    promptTextShowed = 0;
                } break;
            }
        }



    }

    public void selectRandomWord(MouseEvent mouseEvent) {
        ArrayList<JishoResponseModel> jishoModelArrayList = JishoResponseModel.getJishoModelArrayList();
        Random rand = new Random();
        currentModel = jishoModelArrayList.get(rand.nextInt(jishoModelArrayList.size()));

        setEnglishText();

        String[] levels = currentModel.getnLevel().toString().split(",");
        StringBuilder displayLevelsString = new StringBuilder();
        for (String level : levels) {
            displayLevelsString.append(level.substring(6, 8)).append("\n");
        }
        level.setText(displayLevelsString.toString());

        // clear canvas of drawings and hints
        clearCanvas(null);
        promptTextShowed = 0;
        showKanasOnCanvas(null);
    }

    public void setPen(){
        Pen.configureGraphicsContext(dojoGraphicsContext);
        dojoCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> Pen.handleMouseDragged(dojoGraphicsContext, e));
        dojoCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> Pen.handleMousePressed(dojoGraphicsContext, e));
        dojoCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> Pen.handleMouseReleased(dojoGraphicsContext, e));
    }

    public void clearCanvas(MouseEvent mouseEvent) {
        dojoGraphicsContext.clearRect(0, 0, dojoCanvas.getWidth(), dojoCanvas.getHeight());
    }

    public void toggleTranslate(MouseEvent mouseEvent) {
        if(currentModel != null){
            if(isTranslateSelected)setJapaneseText();
            else setEnglishText();
            isTranslateSelected = !isTranslateSelected;
        }
    }

    private void setJapaneseText(){
        word.setText(currentModel.getWord());
        word.setFont(new Font(39));
        reading.setText(currentModel.getReading());
        reading.setFont(new Font(30));
    }

    private void setEnglishText(){
        word.setText(currentModel.getPartsOfSpeech().toString().substring(1, currentModel.getPartsOfSpeech().toString().length() - 1));
        word.setFont(new Font(16));
        reading.setText(currentModel.getEnglishDefinition().toString().substring(1, currentModel.getEnglishDefinition().toString().length() - 1));
        reading.setFont(new Font(15));
    }

    public void chooseLanguageLevel(ActionEvent actionEvent) {
        JishoResponseModel.setLevel(levelComboBox.getValue().toString());
        JishoResponseModel.setJishoModelArrayList(JishoResponseModel.getWords(JishoResponseModel.getUrl() + JishoResponseModel.getLevel()));
        selectRandomWord(null);
    }

    public void goBackToStatisticsScreen(MouseEvent mouseEvent) throws IOException {
        Stage stage = Statistics.getMainAppStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../statistics/statistics.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
}
