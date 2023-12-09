package com.example.game;

import javafx.scene.image.ImageView;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private PlatformManager platformManager;
    private int score;
    private ImageView heroimage;
    private ImageView cherryimage;
    private int cherryScore;
    private SerializablePoint heroC;
    private SerializablePoint cherryC;
    private double plat1X;
    private double plat1Y;
    private double plat1W;
    private double plat2X;
    private  double plat2Y;
    private double plat2W;

    private GameState(double plat1X,double plat1Y,double plat1W,double plat2X,double plat2Y,double plat2W, int score, SerializablePoint heroImage, SerializablePoint imageView, int cherryLabelScore) {
        this.plat1X=plat1X;
        this.plat1Y=plat1Y;
        this.plat1W=plat1W;
        this.plat2X=plat2X;
        this.plat2Y=plat2Y;
        this.plat2W=plat2W;
        this.score = score;
        this.heroC=heroImage;
        this.cherryC=imageView;
        this.cherryScore=cherryLabelScore;
    }
    private static final Map<String, GameState> gameStateCache = new HashMap<>();

    public static GameState getGameState(double plat1X, double plat1Y, double plat1W,
                                         double plat2X, double plat2Y, double plat2W,
                                         int score, SerializablePoint heroImage,
                                         SerializablePoint cherryImage, int cherryLabelScore) {
        String key = getKey(plat1X, plat1Y, plat1W, plat2X, plat2Y, plat2W, score, heroImage, cherryImage, cherryLabelScore);

        if (!gameStateCache.containsKey(key)) {
            GameState gameState = new GameState(plat1X, plat1Y, plat1W, plat2X, plat2Y, plat2W, score, heroImage, cherryImage, cherryLabelScore);
            gameStateCache.put(key, gameState);
        }

        return gameStateCache.get(key);
    }

    private static String getKey(double plat1X, double plat1Y, double plat1W,
                                 double plat2X, double plat2Y, double plat2W,
                                 int score, SerializablePoint heroImage,
                                 SerializablePoint cherryImage, int cherryLabelScore) {
        return plat1X + "_" + plat1Y + "_" + plat1W + "_" +
                plat2X + "_" + plat2Y + "_" + plat2W + "_" +
                score + "_" + heroImage + "_" + cherryImage + "_" + cherryLabelScore;
    }

    public PlatformManager getPlatformManager() {
        return this.platformManager;
    }

    public int getScore() {
        return this.score;
    }
    public ImageView getHeroimage(){
        return heroimage;
    }
    public ImageView getCherryimage(){
        return cherryimage;
    }
    public int getCherryScore(){
        return this.cherryScore;
    }

    public SerializablePoint getHeroCoordinates() {
        return this.heroC;
    }

    public SerializablePoint getCherryCoordinates() {
        return this.cherryC;
    }

    public double getCurrPlatformX() {
        return this.plat1X;
    }

    public double getNextPlatformX() {
        return this.plat2X;
    }

    public double getCurrPlatformY() {
        return this.plat1Y;
    }

    public double getNextPlatformY() {
        return this.plat2Y;
    }

    public double getCurrPlatformWidth() {
        return this.plat1W;
    }

    public double getNextPlatformWidth() {
        return this.plat2W;
    }
}
