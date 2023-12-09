package com.example.game;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class StickHeroGame extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int SPEED = 10;

    private Rectangle player;
    private Rectangle obstacle;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        player = new Rectangle(100, 100, 50, 50);
        player.setFill(Color.BLUE);

        obstacle = new Rectangle(200, 100, 50, 50);
        obstacle.setFill(Color.RED);

        root.getChildren().addAll(player, obstacle);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    player.setY(player.getY() - SPEED);
                    break;
                case DOWN:
                    player.setY(player.getY() + SPEED);
                    break;
                case LEFT:
                    player.setX(player.getX() - SPEED);
                    break;
                case RIGHT:
                    player.setX(player.getX() + SPEED);
                    break;
            }
        });

        primaryStage.setTitle("Stick Hero Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
