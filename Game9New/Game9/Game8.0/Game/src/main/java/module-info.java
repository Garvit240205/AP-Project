module Game {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires java.desktop;

    opens com.example.game to javafx.fxml, org.junit.jupiter.api;
    exports com.example.game;
}
