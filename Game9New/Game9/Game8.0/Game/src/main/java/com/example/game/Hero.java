package com.example.game;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Hero implements Serializable {
    private static final double speed=3;
    private static final long serialVersionUID = 1L;
    private ImageView heroimageView;
    private double x;
    private static Hero instance;
    private double y;double distanceMoved=0;

    public Hero(){
        Image heroimage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("stick-hero - Copy.png")))); // Replace with your image file path
        this.heroimageView = new ImageView(heroimage);
        this.heroimageView.setFitWidth(40);
        this.heroimageView.setFitHeight(40);
        this.heroimageView.setX(this.x);
        this.heroimageView.setY(this.y);
    }




    public void moveRight(boolean flag) {
        if(flag){
            double currentX = this.getHeroImage().getX();
            this.getHeroImage().setX(currentX + 2);
        }
    }

    public boolean isOnPlatform(Platform platform) {
        double currentX = this.getHeroImage().getX()+40;
        double platformX = platform.getX();
        return currentX >= platformX;
    }


    public void fall() {
        double currentY = this.getHeroImage().getY();
        double currentX=this.getHeroImage().getX();

        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), this.getHeroImage());
        fallTransition.setToY(currentY + 60);
        fallTransition.play();
    }

    public void setHeroCoordinates(double x,double y){
        this.x=x;
        this.y=y;
        this.heroimageView.setX(this.x);
        this.heroimageView.setY(this.y);
    }
    public SerializablePoint getHeroCoordinates() {
        return new SerializablePoint(this.x, this.y);
    }

    public ImageView getHeroImage(){
        return this.heroimageView;
    }

    public void setInitialPosition(double x, double y) {
        this.heroimageView.setX(x);
        this.heroimageView.setY(y);
    }

    public boolean notoncurrplatform(Hero hero,PlatformManager platform){
        double currentX=hero.heroimageView.getX();
        if(currentX>platform.getCurrPlatform().getWidth()){
            return true;
        }
        return false;
    }
    private boolean movingDown = false;

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public boolean isMovingDown() {
        return movingDown;
    }
    public void movedown() {
        if (isMovingDown()) {
            double newY = getHeroImage().getY() + 50;
            getHeroImage().setY(newY);
        }
    }
    public void moveup(){
        if(isMovingDown()){
            double newY = getHeroImage().getY() - 50;
            getHeroImage().setY(newY);
        }
    }

    @Override
    public String toString() {
        return "Hero{" +
                "heroimageView=" + heroimageView +
                ", x=" + x +
                ", y=" + y +
                ", distanceMoved=" + distanceMoved +
                ", movingDown=" + movingDown +
                ", collectedCherries=" + collectedCherries +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero hero)) return false;
        return Double.compare(x, hero.x) == 0 && Double.compare(y, hero.y) == 0 && Double.compare(distanceMoved, hero.distanceMoved) == 0 && movingDown == hero.movingDown && Objects.equals(heroimageView, hero.heroimageView) && Objects.equals(collectedCherries, hero.collectedCherries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heroimageView, x, y, distanceMoved, movingDown, collectedCherries);
    }

    public void reversefall(Hero hero, PlatformManager platformManager){
        if(isMovingDown()){
            if(hero.getHeroImage().getX()>=platformManager.getNextPlatform().getX()){
                hero.fall();
            }
        }
    }
    private List<Cherry> collectedCherries = new ArrayList<>();

    public boolean collectCherry(Cherry cherry) {
        if (cherry.getImageView().getX()<=this.heroimageView.getX()) {
            collectedCherries.add(cherry);
            return true;
        }
        return false;
    }
}

