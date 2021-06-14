package canvas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Brush;
import models.KanaProgress;
import models.LearningMode;
import models.Pen;
import roundResults.RoundResults;
import services.DBCommunication;
import statistics.Statistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CanvasController implements Initializable, DBCommunication {
    @FXML
    public Label kanaLabel;
    @FXML
    public ImageView thinking_face;
    @FXML
    public ButtonBar thinkingFaceButton;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private HBox progressHbox;

    private GraphicsContext graphicsContext;
    private static final int ROUNDS_COUNT = 5;
    private static int currentRound;
    //todo incorrect count, always 1 too few, nie resetuje sie wartosc po kilku treningach -> solved??
    private static int masteredThisRound = 0;
    private static String[] morae;

    private static ArrayList<KanaProgress> currentSet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentRound = 0;
        masteredThisRound = 0;
        ArrayList<KanaProgress> all = readKanas();
        graphicsContext = mainCanvas.getGraphicsContext2D();
        currentSet = new ArrayList<>();
        if(canvas.Canvas.getMode().equals(LearningMode.TRAINING)){
            morae = chooseMoraeTraining();
            // disables hints - not needed in training mode
            thinking_face.setImage(null);
            thinkingFaceButton.setOnMouseClicked(null);
            thinking_face.setCursor(Cursor.DEFAULT);
        } else morae = chooseMoraeChallenge();
        drawTrainingProgressHBox(morae);
    }

    private void drawTrainingProgressHBox(String[] morae){
        progressHbox.setSpacing(20);
        for (int i = 0; i < ROUNDS_COUNT; i++) {
            if(i == currentRound){
                Label label = new Label(morae[i]);
                label.setFont(new Font("System", 30));
                progressHbox.getChildren().add(label);
            } else {
                FileInputStream input;
                try {
                    input = new FileInputStream("src\\assets\\dot.png");
                    Image image = new Image(input);
                    ImageView imageView = new ImageView(image);
                    progressHbox.getChildren().add(imageView);
                } catch (FileNotFoundException e) {
                    Label label = new Label("⚫");
                    label.setFont(new Font("System", 30));
                    progressHbox.getChildren().add(label);
                }
            }


        }

    }

    private String[] chooseMoraeChallenge(){
        // mastered < practice && mastered < MASTERY
        ArrayList<KanaProgress> challengeKanas = getKanasForChallenge(ROUNDS_COUNT);
        String[] result = new String[ROUNDS_COUNT];

        currentSet.addAll(challengeKanas);

        for (int i = 0; i < challengeKanas.size(); i++) {
            String mora = challengeKanas.get(i).getMora();
            for (int j = 0; j < mora.length(); j++) {
                Character.UnicodeBlock b = Character.UnicodeBlock.of(mora.charAt(j));
                if (b == Character.UnicodeBlock.HIRAGANA) result[i] = String.format("%s (hiragana)", challengeKanas.get(i).getRomanji());
                if (b == Character.UnicodeBlock.KATAKANA) result[i] = String.format("%s (katakana)", challengeKanas.get(i).getRomanji());
            }

        }
        return result;
    }
    private String[] chooseMoraeTraining(){
        ArrayList<KanaProgress> trainingKanas = getKanasForTraining(ROUNDS_COUNT);
        String[] result = new String[ROUNDS_COUNT];

        currentSet.addAll(trainingKanas);

        for (int i = 0; i < trainingKanas.size(); i++) {
            String mora = trainingKanas.get(i).getMora();
            for (int j = 0; j < mora.length(); j++) {
                Character.UnicodeBlock b = Character.UnicodeBlock.of(mora.charAt(j));
                if (b == Character.UnicodeBlock.HIRAGANA) result[i] = String.format("%s (%s)", trainingKanas.get(i).getMora(), trainingKanas.get(i).getRomanji());
                if (b == Character.UnicodeBlock.KATAKANA) result[i] = String.format("%s (%s)", trainingKanas.get(i).getMora(), trainingKanas.get(i).getRomanji());
            }

        }
        return result;
    }

    public void needPractice(){
        if(currentRound < ROUNDS_COUNT) {
            currentSet.get(currentRound).increasePracticeCount(1);
            currentSet.get(currentRound).increaseRepetitionsCount(1);
            updateKana(currentSet.get(currentRound));
            nextRound();
        }
        System.out.println("practice");
    }

    public void mastered(){
        if(currentRound < ROUNDS_COUNT) {
            System.out.println("round " + currentRound + "/" + ROUNDS_COUNT);
            masteredThisRound++;
            currentSet.get(currentRound).increaseMasteredCount(1);
            currentSet.get(currentRound).increaseRepetitionsCount(1);
            updateKana(currentSet.get(currentRound));
            nextRound();
        }
    }
    public void dontKnow() {
        if(currentRound < ROUNDS_COUNT) {
            currentSet.get(currentRound).increaseDontKnowCount(1);
            currentSet.get(currentRound).increaseMasteredCount(-1);
            currentSet.get(currentRound).increaseRepetitionsCount(1);
            updateKana(currentSet.get(currentRound));
            nextRound();
        }
    }

    private void nextRound(){
        System.out.println(currentRound);
        if(currentRound == ROUNDS_COUNT - 1){
            setMasteredCount();
            try {
                goToRoundResultsScreen();
            } catch (IOException e) {
                System.out.println("Cannot display round results screen");
            }

        }else{
            kanaLabel.setText("");
            currentRound++;
            progressHbox.getChildren().clear();
            graphicsContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
            drawTrainingProgressHBox(morae);
        }

    }

    public void changeToPen(MouseEvent mouseEvent) {
        Pen.configureGraphicsContext(graphicsContext);
        // remove brush event handlers
        mainCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {});
        mainCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, event -> {});
        mainCanvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, event -> {});
        // add pen event handlers
        mainCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> Pen.handleMouseDragged(graphicsContext, e));
        mainCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> Pen.handleMousePressed(graphicsContext, e));
        mainCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> Pen.handleMouseReleased(graphicsContext, e));

    }

    public void changeToBrush(MouseEvent mouseEvent) {
        Brush.configureGraphicsContext(graphicsContext);
        // remove pen event handlers
        mainCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {});
        mainCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, event -> {});
        mainCanvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, event -> {});
        // add brush event handlers
        mainCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> Brush.handleMouseDragged(graphicsContext, e));
        mainCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> Brush.handleMousePressed(graphicsContext, e));
        mainCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> Brush.handleMouseReleased(graphicsContext, e));
    }

    public void goBackToStatisticsScreen(MouseEvent mouseEvent) throws IOException {
        Stage stage = Statistics.getMainAppStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../statistics/statistics.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void goToRoundResultsScreen() throws IOException {
        Stage stage = Statistics.getMainAppStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../roundResults/roundResults.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void showKana(MouseEvent mouseEvent) {
        kanaLabel.setText(currentSet.get(currentRound).getMora());
    }

    public void setMasteredCount(){
        RoundResults.setMasteredThisRound(masteredThisRound);
    }
}
