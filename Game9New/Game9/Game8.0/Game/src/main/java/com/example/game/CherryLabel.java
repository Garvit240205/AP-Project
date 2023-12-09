package com.example.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.Serializable;

public class CherryLabel extends GameLabel implements Serializable {
    private static final long serialVersionUID = 1L;
    Text scoreText = new Text();

    Rectangle background = new Rectangle();
    public CherryLabel() {

        scoreText.setText("0");
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        scoreText.setX(305);
        scoreText.setY(36);
        scoreText.setFill(Color.WHITE);


    }
    public CherryLabel(int q) {

        scoreText.setText("0");
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        scoreText.setX(310);
        scoreText.setY(30);
        scoreText.setFill(Color.WHITE);


    }

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