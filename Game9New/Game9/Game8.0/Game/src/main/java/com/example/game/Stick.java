package com.example.game;

import javafx.animation.RotateTransition;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;

import java.util.Objects;

public class    Stick {
    private ImageView imageView;
    private Timeline timeline;
    private double x;
    private double y;

    public Stick() {
        this.imageView = new ImageView(new Image((Objects.requireNonNull(getClass().getResourceAsStream("pillar1 - Copy.jpg")))));
        this.imageView.setFitWidth(10);
        this.imageView.setFitHeight(1);
        this.imageView.setX(this.x);
        this.imageView.setY(this.y);
        this.timeline = new Timeline(new KeyFrame(
                Duration.millis(6),
                ae -> enlarge()
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void enlarge() {

        imageView.setFitHeight(imageView.getFitHeight() + 1);
        imageView.setY(imageView.getY() - 1);
    }
    public void setCoordinates(double x,double y){
        this.x=x;
        this.y=y;
        this.imageView.setX(this.x);
        this.imageView.setY(this.y);
    }

    public boolean isLaying() {
        return imageView.getFitHeight() > 0;
    }
    public double getStickLength() {
        return imageView.getFitHeight();
    }
    public void move() {
        imageView.setX(imageView.getX() + 1);
    }
    public boolean startEnlarging(MouseEvent event,boolean flag,double x,double y) {
        if(flag){
            this.imageView.setFitWidth(10);
            this.imageView.setFitHeight(0);
            this.imageView.setX(x);
            this.imageView.setY(y);
            this.imageView.setRotate(0);
            this.imageView.getTransforms().clear();
            timeline.play();
            return true;
        }
        return false;
    }

    public boolean stopEnlargingAndLay(MouseEvent event,boolean flag) {
        if(flag){
            timeline.stop();
            double pivotX = imageView.getX() + imageView.getFitWidth() / 2;
            double pivotY = imageView.getY() + imageView.getFitHeight();
            Rotate rotate = new Rotate(0, pivotX, pivotY);
            imageView.getTransforms().add(rotate);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.01), e -> {
                        if (rotate.getAngle() < 90) {
                            rotate.setAngle(rotate.getAngle() + 10);
                        }
                    })
            );

            timeline.setCycleCount(Timeline.INDEFINITE);

            timeline.play();
            return true;
        }
        return false;
    }
    public void fallAnimation() {
        double pivotX = imageView.getX() + imageView.getFitWidth() / 2;
        double pivotY = imageView.getY() + imageView.getFitHeight();
        Rotate rotate = new Rotate(0, pivotX, pivotY);
        imageView.getTransforms().add(rotate);
        final Timeline[] timeline = new Timeline[1];
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.01), e -> {
            if (rotate.getAngle() < 90) {
                rotate.setAngle(rotate.getAngle() + 2);
            } else {
                timeline[0].stop();
            }
        });

        timeline[0] = new Timeline(keyFrame);
        timeline[0].setCycleCount(Timeline.INDEFINITE);

        timeline[0].play();
    }
    public ImageView getImageView() {
        return imageView;
    }
}

