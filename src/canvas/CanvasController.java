package canvas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import statistics.Statistics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CanvasController implements Initializable, ReadWriteCsv {
    @FXML
    private Canvas canvas;
    @FXML
    private HBox progressHbox;

    private GraphicsContext graphicsContext;
    private static final int ROUNDS_COUNT = 5;
    private static int currentRound = 0;
    private static String[] morae;

    private static ArrayList<KanaProgress> currentSet;
    private  ArrayList<KanaProgress> all;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        all = getUserProgress();
        graphicsContext = canvas.getGraphicsContext2D();
        currentSet = new ArrayList<>();
        morae = chooseMoraeChallenge();
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
        String[] result = new String[ROUNDS_COUNT];

        Random rand = new Random();
        for (int i = 0; i < ROUNDS_COUNT; i++) {
            KanaProgress kanaProgress = all.get(rand.nextInt(all.size()));
            currentSet.add(kanaProgress);
            result[i] = kanaProgress.getMora();
        }
        return result;
    }
    private String[] chooseMoraeTraining(){
        Map<String, String> map = readCsvConvertToMap();
        List<String> keys = new ArrayList<>(map.keySet());
        String[] result = new String[ROUNDS_COUNT];

        Random rand = new Random();
        for (int i = 0; i < ROUNDS_COUNT; i++) {
            String key = keys.get(rand.nextInt(keys.size()));
            result[i] = key;
//            for (int j = 0; j < key.length(); j++) {
//                Character.UnicodeBlock b = Character.UnicodeBlock.of(key.charAt(j));
//
//                if (b == Character.UnicodeBlock.HIRAGANA) System.out.println(" hiragana");
//                if (b == Character.UnicodeBlock.KATAKANA) System.out.println(" katakana");
//            }
        }
        return result;
    }
    public void needPractice(){
        System.out.println("practice");
    }

    public void mastered(){
        if(currentRound <= ROUNDS_COUNT) {
            currentSet.get(currentRound).increaseMasteredCount(1);
            writeUserProgressToCsv(currentSet.get(currentRound));
            nextRound();
        }
        System.out.println("mastered");
    }
    public void dontKnow() {
        System.out.println("idk");
    }

    public void nextRound(){
        if(currentRound == ROUNDS_COUNT){
            // todo show another screen with results
            // save to csv
        }else{
            currentRound++;
            progressHbox.getChildren().clear();
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawTrainingProgressHBox(morae);
        }

    }

    public void changeToPen(MouseEvent mouseEvent) {
        Pen.configureGraphicsContext(graphicsContext);
        // remove brush event handlers
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, e -> Brush.handleMouseDragged(graphicsContext, e));
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, e -> Brush.handleMousePressed(graphicsContext, e));
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, e -> Brush.handleMouseReleased(graphicsContext, e));
        // add pen event handlers
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> Pen.handleMouseDragged(graphicsContext, e));
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> Pen.handleMousePressed(graphicsContext, e));
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> Pen.handleMouseReleased(graphicsContext, e));

    }

    public void changeToBrush(MouseEvent mouseEvent) {
        System.out.println("brush");
        // remove pen event handlers
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, e -> Pen.handleMouseDragged(graphicsContext, e));
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, e -> Pen.handleMousePressed(graphicsContext, e));
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, e -> Pen.handleMouseReleased(graphicsContext, e));
        // add brush event handlers
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> Brush.handleMouseDragged(graphicsContext, e));
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> Brush.handleMousePressed(graphicsContext, e));
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> Brush.handleMouseReleased(graphicsContext, e));
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
