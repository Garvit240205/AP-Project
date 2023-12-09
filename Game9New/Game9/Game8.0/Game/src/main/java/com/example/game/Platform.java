package com.example.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Objects;

public class Platform implements Serializable {
    private static final long serialVersionUID = 1L;
    private SerializedRectangle rectangle; // The Rectangle that represents the platform
    private double x;
    private double y;

    @Override
    public String toString() {
        return "Platform{" +
                "rectangle=" + rectangle +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Platform platform)) return false;
        return Double.compare(x, platform.x) == 0 && Double.compare(y, platform.y) == 0 && Double.compare(width, platform.width) == 0 && Objects.equals(rectangle, platform.rectangle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rectangle, x, y, width);
    }

    private double width;

    public Platform(double x, double y, double width) {
        this.rectangle = new SerializedRectangle();
        this.x=x;
        this.y=y;
        this.width=width;
        this.rectangle.setX(x);
        this.rectangle.setY(y);
        this.rectangle.setWidth(width);
        this.rectangle.setHeight(170);
        this.rectangle.setFill(Color.BLACK);
    }

    public double getX(){
        return this.rectangle.getX();
    }

    public double getY() {
        return this.rectangle.getY();
    }
    public double getWidth(){
        {
            return this.width;
        }
    }
    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
