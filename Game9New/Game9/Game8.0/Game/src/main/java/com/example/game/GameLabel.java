package com.example.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.Serializable;

public class GameLabel implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Text labelText = new Text();
    protected Rectangle background = new Rectangle();

    protected GameLabel() {
        labelText.setText("0");
        labelText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        labelText.setX(305);
        labelText.setY(36);
        labelText.setFill(Color.WHITE);
    }

    public void updateLabel(int value) {
        labelText.setText(String.valueOf(value));
    }

    public Rectangle getBackground() {
        return background;
    }

    public Text getLabelText() {
        return labelText;
    }

    public int getValue() {
        return Integer.parseInt(labelText.getText());
    }
}
