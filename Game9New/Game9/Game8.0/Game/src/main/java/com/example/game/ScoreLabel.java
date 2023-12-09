package com.example.game;

import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.Serializable;

public class ScoreLabel extends GameLabel implements Serializable {
    Text scoreText = new Text();
    private static final long serialVersionUID = 1L;
    Rectangle background = new Rectangle();
    public ScoreLabel() {

        // Set up the background
        background.setWidth(100);
        background.setHeight(50);
        background.setX(135);
        background.setY(138);
        background.setFill(Color.web("#404040"));

        scoreText.setText("0");
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        scoreText.setX(170);
        scoreText.setY(180);
        scoreText.setFill(Color.WHITE);


    }
//    public ScoreLabel(int q) {
//
//        // Set up the background
//        background.setWidth(100); // Sets the width of the background
//        background.setHeight(50); // Sets the height of the background
//        background.setX(135); // Sets the X position of the background
//        background.setY(138); // Sets the Y position of the background
//        background.setFill(Color.web("#404040")); // Sets the color of the background to light black
//
//        scoreText.setText("0");
//        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
//        scoreText.setX(170); // Sets the X position of the text
//        scoreText.setY(180); // Sets the Y position of the text
//        scoreText.setFill(Color.BLACK); // Sets the color of the text
//
//
//    }

    public void updateScore(int score) {
        scoreText.setText(String.valueOf(score));
    }

    public Rectangle getBackground() {
        return background;
    }

    public Text getScoreText() {
        return scoreText;
    }

    public int getScore() {
        return (Integer.parseInt(scoreText.getText()));
    }
}