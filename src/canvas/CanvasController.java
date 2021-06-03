package canvas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import models.Pen;
import pdo.ReadWriteCsv;
import services.DBCommunication;
import statistics.Statistics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CanvasController implements Initializable, DBCommunication, ReadWriteCsv {
    public Label kanaLabel;
    public ButtonBar thnkingFaceButton;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private HBox progressHbox;

    private GraphicsContext graphicsContext;
    private static final int ROUNDS_COUNT = 5;
    private static int currentRound;
    private static String[] morae;

    private static ArrayList<KanaProgress> currentSet;
    private  ArrayList<KanaProgress> all;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentRound = 0;
        all = readKanas();
        graphicsContext = mainCanvas.getGraphicsContext2D();
        currentSet = new ArrayList<>();
        //todo training / challenge
        if(canvas.Canvas.getMode().equals("training")){
            morae = chooseMoraeTraining();
        } else morae = chooseMoraeChallenge();
        drawTrainingProgressHBox(morae);
    }

    private void drawTrainingProgressHBox(String[] morae){
        for (int i = 0; i < ROUNDS_COUNT; i++) {
            if(i == currentRound){
                Label label = new Label(morae[i]);
                label.setFont(new Font("System", 30));
                progressHbox.getChildren().add(label);
            }
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

    private String[] chooseMoraeChallenge(){
        // mastered < practice && mastered < MASTERY
        ArrayList<KanaProgress> challengeKanas = getKanasForChallenge(ROUNDS_COUNT);
        System.out.println(challengeKanas);
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
        // najmniejszy progress

        // get kanas that are marked dont know then ones that have 0 practice count
        // String.format("%s (%s)", challengeKanas.get(i).getMora(), challengeKanas.get(i).getRomanji()
        ArrayList<KanaProgress> trainingKanas = getKanasForTraining(ROUNDS_COUNT);
        System.out.println(trainingKanas);
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
            System.out.println(currentSet.get(currentRound).getRomanji());
            currentSet.get(currentRound).increasePracticeCount(1);
            currentSet.get(currentRound).increaseRepetitionsCount(1);
            updateKana(currentSet.get(currentRound));
            nextRound();
        }
        System.out.println("practice");
    }

    public void mastered(){
        if(currentRound < ROUNDS_COUNT) {
            currentSet.get(currentRound).increaseMasteredCount(1);
            currentSet.get(currentRound).increaseRepetitionsCount(1);
            updateKana(currentSet.get(currentRound));
            nextRound();
        }
        System.out.println("mastered");
    }
    public void dontKnow() {
        if(currentRound < ROUNDS_COUNT) {
            System.out.println(currentSet.get(currentRound).getRomanji());
            currentSet.get(currentRound).increaseDontKnowCount(1);
            currentSet.get(currentRound).increaseMasteredCount(-1);
            currentSet.get(currentRound).increaseRepetitionsCount(1);
            updateKana(currentSet.get(currentRound));
            nextRound();
        }
        System.out.println("idk");
    }

    public void nextRound(){
        if(currentRound == ROUNDS_COUNT){
            // todo show another screen with results
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
        System.out.println("brush");
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

    public void showKana(MouseEvent mouseEvent) {
        kanaLabel.setText(currentSet.get(currentRound).getMora());
    }
}
