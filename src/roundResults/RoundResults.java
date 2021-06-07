package roundResults;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RoundResults extends Application {
    private static int masteredThisRound;

    public static int getMasteredThisRound() {
        return masteredThisRound;
    }

    public static void setMasteredThisRound(int newCount) {
        masteredThisRound = newCount;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("roundResults.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
