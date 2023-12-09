module com.example.game.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires Game;

    opens com.example.test to javafx.fxml, org.junit.jupiter.api,org.junit.platform.commons;
    exports com.example.test;
}
