package roundResults;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import statistics.Statistics;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoundResultsController implements Initializable {

    public ImageView image;
    public Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int masteredCount = RoundResults.getMasteredThisRound();

        if(masteredCount > 0) label.setText(""+masteredCount);

        File file = new File(String.format("src\\assets\\%s.png", masteredCount));
        Image img = new Image(file.toURI().toString());

        image.setImage(img);

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
