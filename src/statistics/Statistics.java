package statistics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Statistics extends Application {
    private static Stage mainAppStage = new Stage();

    public static Stage getMainAppStage() {
        return mainAppStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        mainAppStage.setScene(scene);
        mainAppStage.show();
    }

}
