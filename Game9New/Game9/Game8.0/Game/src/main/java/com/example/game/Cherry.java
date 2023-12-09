package com.example.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Objects;

public class Cherry implements Serializable {
    private ImageView imageView;
    private static final long serialVersionUID = 1L;
    private double x;
    private double y;

    @Override
    public String toString() {
        return "Cherry{" +
                "imageView=" + imageView +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cherry cherry)) return false;
        return Double.compare(x, cherry.x) == 0 && Double.compare(y, cherry.y) == 0 && Objects.equals(imageView, cherry.imageView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageView, x, y);
    }

    public Cherry(String imagePath, double x, double y, double width, double height) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cherry.png")), width, height, false, false);
        this.imageView = new ImageView(image);
        this.imageView.setX(x);
        this.imageView.setY(y);
        this.x=x;
        this.y=y;
    }
    public Cherry(){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cherry.png")), 22, 22, false, false);
        this.imageView = new ImageView(image);
    }
    public ImageView getImageView() {
        return this.imageView;
    }
    public void setCherryCoordinates(double x,double y){
        this.x=x;
        this.y=y;
        this.imageView.setX(this.x);
        this.imageView.setY(this.y);
    }
    public SerializablePoint getCherryCoordinates() {
        return new SerializablePoint(this.x, this.y);
    }

    public void setX(double x) {
        this.imageView.setX(x);
    }

    public void setY(double y) {
        imageView.setY(y);
    }
}