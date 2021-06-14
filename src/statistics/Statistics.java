package statistics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Statistics extends Application {
    private static Stage mainAppStage = new Stage();

    public static Stage getMainAppStage() {
        return mainAppStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainAppStage.getIcons().add(new Image("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/60/apple/51/mount-fuji_1f5fb.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        mainAppStage.setScene(scene);
        mainAppStage.show();
    }

}
