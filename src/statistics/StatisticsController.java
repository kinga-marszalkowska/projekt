package statistics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pdo.ReadCsv;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    @FXML
    public GridPane charsGridpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println(ReadCsv.readCsv("D:\\PJATK\\POJ\\lab11-copy\\src\\pdo\\kana_to_romanji.csv"));

        for (int i = 0; i < charsGridpane.getRowCount(); i++) {
            for (int j = 0; j < charsGridpane.getColumnCount(); j++) {
                charsGridpane.add(new Label("A"), i, j);
            }
        }


    }
    public void goToCanvasScreen(MouseEvent mouseEvent) throws IOException {
        Stage stage = Statistics.getMainAppStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../canvas/canvas.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void showNextChars(MouseEvent mouseEvent) {
        System.out.println("next");
    }

    public void showPrevChars(MouseEvent mouseEvent) {
        System.out.println("showPrev");
    }
}
