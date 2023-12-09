package com.example.game;

import javafx.scene.shape.Rectangle;

import java.io.Serial;
import java.io.Serializable;

public class SerializedRectangle extends Rectangle implements Serializable {
    private static final long serialVersionUID = 1L;


    public SerializedRectangle() {
        super();
    }
}
