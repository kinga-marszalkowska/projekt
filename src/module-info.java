module lab11{
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires org.json;

    opens canvas;
    opens statistics;
}