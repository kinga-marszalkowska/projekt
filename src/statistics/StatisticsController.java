package statistics;

import canvas.Canvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.KanaProgress;
import models.LearningMode;
import services.DBCommunication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable, DBCommunication {
    @FXML
    public GridPane charsGridpane;

    private ArrayList<KanaProgress> all;

    // all kanas are divided into multiple pages that they can be browsed by clicking navigation arrows
    // kanaPageNumber keeps track of which page should be displayed
    private int moraPageNumber = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        all = readKanas();
        fillGridpaneWithElements();
    }

    private void fillGridpaneWithElements(){
         /*
           Use stream to skip first n characters
          ex. if on page 0 - no elements will be skipped,
              if on page 1 - first {size} elements will be skipped
          */
        int size = charsGridpane.getColumnCount() * charsGridpane.getRowCount();
        Object[] charsForCurrentPage = all.stream().skip(moraPageNumber *size).toArray();

        // if no elements are left to display, come back to page no.0
        if(charsForCurrentPage.length == 0) {
            moraPageNumber = 0;
            charsForCurrentPage = all.stream().skip(moraPageNumber *size).toArray();
        }

        int column = 0;
        int row = 0;

        for (Object japaneseChar : charsForCurrentPage) {
            if(size > 0){
                if(column < charsGridpane.getColumnCount()){
                    if(row < charsGridpane.getRowCount()){
                        insertElementToGridpane((KanaProgress)japaneseChar, row, column);
                        size--;
                        row++;
                    } else {
                        row = 0;
                        column ++;
                    }
                } else {
                    column = 0;
                    row++;
                }
            } else break;

        }
    }


    private void insertElementToGridpane(KanaProgress japaneseChar, int row, int column){
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(
                new Background(
                new BackgroundFill(Color.rgb(250, 250, 250, 0.8),
                new CornerRadii(15),
                        new Insets(5,10,5,10))));
        Label japaneseCharLabel = new Label(japaneseChar.getMora());
        japaneseCharLabel.setFont(new Font("System", 30));

        Label romanjiCharLabel = new Label(japaneseChar.getRomanji());
        romanjiCharLabel.setFont(new Font("System", 20));

        ProgressBar progressBar = new ProgressBar(japaneseChar.getProgress());
        progressBar.setPrefSize(70, 12);

        vBox.getChildren().addAll(japaneseCharLabel, romanjiCharLabel, progressBar);
        charsGridpane.add(vBox, row, column);
    }

    public void showNextChars(MouseEvent mouseEvent) {
        moraPageNumber++;
        // remove all elements before inserting new ones to prevent drawing on top of the old gridpane
        charsGridpane.getChildren().clear();
        fillGridpaneWithElements();
    }

    public void showPrevChars(MouseEvent mouseEvent) {
        if(moraPageNumber > 0) moraPageNumber--;
        charsGridpane.getChildren().clear();
        fillGridpaneWithElements();
    }

    public void goToCanvasScreen(MouseEvent mouseEvent) throws IOException {
        Stage stage = Statistics.getMainAppStage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../canvas/canvas.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void goToDojoScreen(MouseEvent mouseEvent) throws IOException {
        Stage stage = Statistics.getMainAppStage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../dojo/dojo.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }


    public void setTrainingMode(ActionEvent actionEvent) {
        Canvas.setMode(LearningMode.TRAINING);
    }

    public void setChallengeMode(ActionEvent actionEvent) {
        Canvas.setMode(LearningMode.CHALLENGE);
    }
}
