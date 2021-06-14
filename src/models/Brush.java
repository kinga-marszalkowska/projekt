package models;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public abstract class Brush {
    private static final Color color = Color.BLACK;
    private static final double START_OPACITY = 0.3;
    private static final double OPACITY_MODIFIER = 0.002;

    private static double currentOpacity = START_OPACITY;
    private static final double STROKE_WIDTH = 10;

    public static void configureGraphicsContext(GraphicsContext gc) {
        gc.setStroke(new Color(color.getRed(), color.getGreen(), color.getBlue(), currentOpacity));
        gc.setLineWidth(10);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        gc.setLineWidth(STROKE_WIDTH);
    }

    public static void handleMousePressed(GraphicsContext gc, MouseEvent e) {
        configureGraphicsContext(gc);
        gc.beginPath();
        gc.moveTo(e.getX(), e.getY());
        gc.stroke();
    }

    public static void handleMouseReleased(GraphicsContext gc, MouseEvent e) {
        currentOpacity = START_OPACITY;
        gc.closePath();
    }

    public static void handleMouseDragged(GraphicsContext gc, MouseEvent e) {
        currentOpacity = Math.max(0, currentOpacity - OPACITY_MODIFIER);
        configureGraphicsContext(gc);
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
    }

}
