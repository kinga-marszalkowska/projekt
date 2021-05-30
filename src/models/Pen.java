package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public abstract class Pen {
    private static int lineWidth = 5;
    private static Color color = Color.BLACK;

    public static void configureGraphicsContext(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(lineWidth);
        gc.setLineCap(StrokeLineCap.SQUARE);
        gc.setLineJoin(StrokeLineJoin.ROUND);
//        gc.setGlobalBlendMode(BlendMode.MULTIPLY);
    }

    public static void handleMousePressed(GraphicsContext gc, MouseEvent e) {
        gc.beginPath();
        gc.moveTo(e.getX(), e.getY());
        gc.stroke();
    }

    public static void handleMouseReleased(GraphicsContext gc, MouseEvent e) {
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
        gc.closePath();
//                        graphicsContext.applyEffect(new DropShadow(50, 20, 0, Color.GREEN));

    }

    public static void handleMouseDragged(GraphicsContext gc, MouseEvent e) {
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
        gc.closePath();
        gc.beginPath();
        gc.moveTo(e.getX(), e.getY());
    }
}
