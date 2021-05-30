package canvas;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import models.Brush;
import models.Pen;
import statistics.Statistics;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CanvasController implements Initializable {
    @FXML
    public Canvas canvas;
    GraphicsContext graphicsContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void needPractice(){
        System.out.println("practice");
    }
    public void mastered(){
        System.out.println("mastered");
    }
    public void dontKnow() {
        System.out.println("idk");
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
