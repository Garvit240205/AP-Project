package com.example.game;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameScreen extends Application{


    Stage window;
    private ExecutorService executorService;
    private boolean flag=true;
    private Timeline gameLoop;
    private Cherry cherry;
    Scene startScene;

    PlatformManager platformManager = new PlatformManager();
    private static GameState gameState;
    boolean stickReleased = false;
    public static boolean revived =false;
    public static boolean home=true;
    public static boolean home2=false;
    private double cherry_x =330;
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Pane pane = new Pane();
        if(home||home2){
            creategamescreen(window);
            home2=false;
        }

        else{
            CherryManager ManageCherry = new CherryManager();

            BackgroundSize backgroundSize = new BackgroundSize(60, 60, true, true, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("background.jpeg.jpg")), 800, 600, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);


            pane.setBackground(new Background(backgroundImage));
            ScoreLabel scoreLabel =(ScoreLabel) createLabel("scorelabel");
            pane.getChildren().addAll(scoreLabel.getBackground(), scoreLabel.getScoreText());

            ManageCherry.addCherry(new Cherry("cherry.png", 80, 500, 30, 30));
            pane.getChildren().add(ManageCherry.getCurrCherry().getImageView());
            Text bkwaas = new Text();
            bkwaas.setText("Hold your finger on screen\n    to stretch out the stick");
            bkwaas.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
            bkwaas.setX(70);
            bkwaas.setY(230);
            bkwaas.setFill(Color.BLACK);
            pane.getChildren().add(bkwaas);

            Hero hero =new Hero();
            pane.getChildren().add(hero.getHeroImage());

            Stick stick = new Stick();
            pane.getChildren().add((stick.getImageView()));
           CherryLabel cherryLabel= (CherryLabel) createLabel("cherrylabel");

            cherryLabel.updateScore(0);
            AtomicBoolean stopflag = new AtomicBoolean(true);
            AtomicBoolean clickAllow = new AtomicBoolean(true);
            AtomicBoolean destinationReachedByHero = new AtomicBoolean(false);
            pane.getChildren().add(cherryLabel.getScoreText());
            AtomicInteger randomPlatformX = new AtomicInteger();

            Random random = new Random();
            Cherry cherry = new Cherry("cherry.png", cherry_x, 15, 22, 22);
            if (!revived) {

                int randomWidth = random.nextInt(31) + 60;
                int randomX = random.nextInt(100) + 140;
                Platform pillar = new Platform(randomX, 480, randomWidth);

                platformManager.addPlatform(new Platform(0, 480, 50));
                platformManager.addPlatform(new Platform(randomX, 480, randomWidth));

                pane.getChildren().add(platformManager.getCurrPlatform().getRectangle());
                pane.getChildren().add(platformManager.getNextPlatform().getRectangle());

                stick.setCoordinates(45, 485);

                hero.setHeroCoordinates(10, 440);
                Platform newpillar = new Platform(randomX, 480, randomWidth);

                pane.getChildren().add(cherry.getImageView());

                Image save = new Image((Objects.requireNonNull(getClass().getResourceAsStream("Save.png"))));
                ImageView imageView = new ImageView(save);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                imageView.setX(320);
                imageView.setY(70);
                pane.getChildren().add(imageView);

                double buttonWidth = 25;
                double buttonHeight = 25;
                Button savebutton = new Button();
                savebutton.setMinSize(buttonWidth, buttonHeight);
                savebutton.setStyle("-fx-shape: \"M50,0 A50,50 0 1,0 100,50 A50,50 0 1,0 0,50 Z\";");
                savebutton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-traversable: false;");
                savebutton.setLayoutX(320);
                savebutton.setLayoutY(70);
                savebutton.setOnAction(event -> saveGameState(scoreLabel, hero, cherryLabel, cherry, pane));
                pane.getChildren().add(savebutton);

                Image load = new Image((Objects.requireNonNull(getClass().getResourceAsStream("load.jpeg"))));
                ImageView loadimageView = new ImageView(load);
                loadimageView.setFitWidth(28);
                loadimageView.setFitHeight(28);
                loadimageView.setX(280);
                loadimageView.setY(70);
                pane.getChildren().add(loadimageView);

                double loadbuttonWidth = 28;
                double loadbuttonHeight = 28;
                Button loadbutton = new Button();
                loadbutton.setMinSize(loadbuttonWidth, loadbuttonHeight);
                loadbutton.setStyle("-fx-shape: \"M50,0 A50,50 0 1,0 100,50 A50,50 0 1,0 0,50 Z\";");
                loadbutton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-traversable: false;");
                loadbutton.setLayoutX(280);
                loadbutton.setLayoutY(70);
                loadbutton.setOnAction(event -> loadGameState(scoreLabel, hero, cherryLabel, cherry, pane,platformManager));
                pane.getChildren().add(loadbutton);

            }
            else if (scoreLabel != null && hero != null && cherryLabel != null && cherry != null && pane != null && platformManager != null && revived == true) {
                loadeachstage(scoreLabel, hero, cherryLabel, cherry, pane, platformManager);
                if(cherryLabel.getScore()-2<0){
                    cherryLabel.updateScore(0);
                }
                else{
                    cherryLabel.updateScore(cherryLabel.getScore()-2);
                }
                revived = false;
            }
            try {
                if (Files.exists(java.nio.file.Path.of("savehighscore.ser")) && Files.size(java.nio.file.Path.of("savehighscore.ser"))  > 0) {

                } else {

                    savehighscore( scoreLabel,1);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                if (Files.exists(java.nio.file.Path.of("savelastscore2.ser")) && Files.size(java.nio.file.Path.of("savelastscore2.ser"))  > 0) {

                    savelastscore2( scoreLabel,0);
                } else {

                    savelastscore2( scoreLabel,1);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            executorService = Executors.newFixedThreadPool(10);
            gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.017), e -> {


                executorService.execute(() -> {
                    javafx.application.Platform.runLater(() -> {
                        startScene.setOnMousePressed(event -> {
                            if (scoreLabel != null && hero != null && cherryLabel != null && cherry != null && pane != null && platformManager != null) {
                                saveeachstage(scoreLabel, hero, cherryLabel, cherry, pane, platformManager);

                            }
                            if (clickAllow.get()) {
                                boolean isreleased = stick.startEnlarging(event, flag, platformManager.getCurrPlatform().getWidth() - 5, platformManager.getCurrPlatform().getY() + 5);
                                if (isreleased) {
                                    stickReleased = false;
                                }
                                if (hero.notoncurrplatform(hero, platformManager)) {
                                    hero.setMovingDown(true);
                                    hero.movedown();
                                    hero.getHeroImage().setScaleY(-1);
                                }
                            }
                        });
                        startScene.setOnMouseClicked(mouseEvent -> {

                        });
                        startScene.setOnMouseReleased(event -> {
                            if (clickAllow.get()) {

                                boolean isenlarged = stick.stopEnlargingAndLay(event, flag);
                                if (isenlarged) {
                                    stickReleased = true;
                                }
                                if (hero.notoncurrplatform(hero, platformManager)) {
                                    hero.moveup();
                                    hero.getHeroImage().setScaleY(1);
                                    hero.setMovingDown(false);
                                }
                            }
                        });
                        if (hero.getHeroImage().getX() < platformManager.getCurrPlatform().getWidth() - 40) {
                            hero.moveRight(stopflag.get());
                        }
                        if (stickReleased) {
                            double stickLength = stick.getStickLength();
                            if (hero.getHeroImage().getX() - 40 < stickLength) {
                                hero.moveRight(stopflag.get());
                                flag = false;
                                if (hero.isMovingDown() && (ManageCherry.getCurrCherry() != null) && (hero.getHeroImage().getX() + 40 >= ManageCherry.getCurrCherry().getImageView().getX()) && hero.getHeroImage().getX()+40<=ManageCherry.getCurrCherry().getImageView().getX()+70) {
                                    pane.getChildren().remove(ManageCherry.getCurrCherry().getImageView());
                                    cherryLabel.updateScore(cherryLabel.getScore() + 1);
                                    scoreLabel.updateScore(scoreLabel.getScore()+1);
                                    ManageCherry.removeCurrCherry();
                                }
                                if (hero.isMovingDown() && hero.getHeroImage().getX() + 40 >= platformManager.getNextPlatform().getX()) {
                                    if (scoreLabel != null && hero != null && cherryLabel != null && cherry != null && pane != null && platformManager != null) {
                                        savelastscore(scoreLabel);
                                    }
                                    if (scoreLabel != null && hero != null && cherryLabel != null && cherry != null && pane != null && platformManager != null) {
                                        savegamescore(scoreLabel);
                                    }

                                    hero.fall();
                                    stopflag.set(false);
                                    Duration delay = Duration.seconds(1);
                                    KeyFrame keyFrame = new KeyFrame(delay, event -> {
                                        Scene gameOverScene = GameOver.createGameOverScene(window, cherryLabel.getScore());
                                        window.setScene(gameOverScene);
                                        gameLoop.stop();
                                    });

                                    Timeline delayTimeline = new Timeline(keyFrame);
                                    delayTimeline.play();

                                }
                            }
                            else {
                                stickReleased = false;

                                if (!hero.isOnPlatform(platformManager.getNextPlatform()) || hero.getHeroImage().getX()+15 > platformManager.getNextPlatform().getWidth() + platformManager.getNextPlatform().getX() || (hero.isMovingDown() && hero.isOnPlatform(platformManager.getNextPlatform())) || hero.getHeroImage().getX() + 20 < platformManager.getNextPlatform().getX()) {
                                    if (scoreLabel != null && hero != null && cherryLabel != null && cherry != null && pane != null && platformManager != null) {
                                        savelastscore(scoreLabel);
                                    }
                                    if (scoreLabel != null && hero != null && cherryLabel != null && cherry != null && pane != null && platformManager != null) {
                                        savegamescore(scoreLabel);
                                    }
                                    hero.fall();
                                    stick.fallAnimation();
                                    revived = false;
                                    Duration delay = Duration.seconds(1);
                                    KeyFrame keyFrame = new KeyFrame(delay, event -> {
                                        Scene gameOverScene = GameOver.createGameOverScene(window, cherryLabel.getScore());
                                        window.setScene(gameOverScene);
                                        gameLoop.stop();
                                    });

                                    Timeline delayTimeline = new Timeline(keyFrame);
                                    delayTimeline.play();
                                    clickAllow.set(true);

                                } else {
                                    if (hero.getHeroImage().getX() < platformManager.getNextPlatform().getWidth() + platformManager.getNextPlatform().getX() - 40) {
                                        hero.moveRight(stopflag.get());
                                        stickReleased = true;
                                        clickAllow.set(false);
                                    } else {
                                        destinationReachedByHero.set(true);
                                        clickAllow.set(true);
                                        flag = true;
                                    }

                                }
                            }
                        } else if (destinationReachedByHero.get()) {

                            if (ManageCherry.getCurrCherry() != null) {
                                pane.getChildren().remove(ManageCherry.getCurrCherry().getImageView());
                                ManageCherry.removeCurrCherry();
                            }

                            double newPlatformOutX = -platformManager.getCurrPlatform().getWidth();
                            KeyValue kvPlatformOut = new KeyValue(platformManager.getCurrPlatform().getRectangle().xProperty(), newPlatformOutX);

                            double newStickX = 530;
                            KeyValue kvStick = new KeyValue(stick.getImageView().yProperty(), newStickX);

                            double newHeroX = platformManager.getNextPlatform().getWidth() - 40;
                            KeyValue kvHero = new KeyValue(hero.getHeroImage().xProperty(), newHeroX);

                            if (platformManager.getPlatforms().size() == 2) {

                                platformManager.removeOldestPlatform();

                                int PlatrandomWidth = random.nextInt(31) + 60;
                                platformManager.addPlatform(new Platform(1000, 480, PlatrandomWidth));
                                pane.getChildren().add(platformManager.getNextPlatform().getRectangle());
                            }
                            int PlatrandomX = random.nextInt(100) + 90 + (int) platformManager.getCurrPlatform().getWidth();

                            double platNew = 0;
                            KeyValue kvNew = new KeyValue(platformManager.getCurrPlatform().getRectangle().xProperty(), platNew);

                            double platCurr = PlatrandomX;
                            KeyValue kvCurr = new KeyValue(platformManager.getNextPlatform().getRectangle().xProperty(), platCurr);

                            KeyFrame kfMove = new KeyFrame(Duration.seconds(0.5), kvPlatformOut, kvStick, kvHero, kvNew, kvCurr);
                            Timeline timelineMove = new Timeline(kfMove);
                            timelineMove.play();
                            destinationReachedByHero.set(false);
                            timelineMove.setOnFinished(event -> {
                                destinationReachedByHero.set(false);
                                scoreLabel.updateScore(scoreLabel.getScore() + 1);
                                if (scoreLabel != null && hero != null && cherryLabel != null && cherry != null && pane != null && platformManager != null) {
                                    savehighscore(scoreLabel, 0);

                                }

                                System.out.println(platformManager.getCurrPlatform().getX() + "andr2 " + platformManager.getNextPlatform().getX() + "\n");
                                int ch = random.nextInt(2);
                                if (ch == 0) {
                                    double cherryX;
                                    double cherryY = 500;
                                    double gapStart = platformManager.getCurrPlatform().getX() + platformManager.getCurrPlatform().getWidth();
                                    double gapEnd = platformManager.getNextPlatform().getX();

                                    if (gapEnd - gapStart > 30) {
                                        cherryX = gapStart + (gapEnd - gapStart) / 2;
                                    } else {
                                        cherryX = gapEnd - 35;
                                    }
                                    cherry_x = cherryX;

                                    Cherry newCherry = new Cherry("cherry.png", cherryX, cherryY, 30, 30);
                                    ManageCherry.addCherry(newCherry);
                                    pane.getChildren().add(ManageCherry.getCurrCherry().getImageView());
                                }
                            });
                        }
                    });
                });
            }));
            gameLoop.setCycleCount(Timeline.INDEFINITE);
            gameLoop.play();
            startScene = new Scene(pane, 360, 640);
            window.setScene(startScene);
            window.setTitle("Stick Hero Game");
            window.show();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void stop() {
        if(executorService!=null){
            if (!executorService.isTerminated()) {
                executorService.shutdownNow();
            }
        }
    }
    public static GameLabel createLabel(String labelType) {// Factory Design Pattern
        if (Objects.equals(labelType, "cherrylabel")) {
            return new CherryLabel();
        } else if (Objects.equals(labelType, "scorelabel")) {
            return new ScoreLabel();
        } else {
            throw new IllegalArgumentException("Unsupported label type");
        }
    }

    public void savehighscore(ScoreLabel scoreLabel,int count){
        int score = loadhighscore();
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savehighscore.ser"))){
            if (count==0){
                if (score < scoreLabel.getScore()) {
                    HighScore high = new HighScore(scoreLabel.getScore());
                    oos.writeObject(high);

                }
                else {
                    HighScore high = new HighScore(score);
                    oos.writeObject(high);
                }
            }
            else{
                HighScore high = new HighScore(0);
                oos.writeObject(high);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveeachstage(ScoreLabel scoreLabel, Hero hero, CherryLabel cherryLabel, Cherry cherry, Pane pane,PlatformManager platformManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegamestage.ser"))) {
            gameState =  GameState.getGameState(platformManager.getCurrPlatform().getX(),platformManager.getCurrPlatform().getY(),platformManager.getCurrPlatform().getWidth(),platformManager.getNextPlatform().getX(),platformManager.getNextPlatform().getY(),platformManager.getNextPlatform().getWidth(), scoreLabel.getScore(),hero.getHeroCoordinates(),cherry.getCherryCoordinates(),cherryLabel.getScore());
            oos.writeObject(gameState);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void savegamescore(ScoreLabel scoreLabel){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegamescore.ser"))){

//            gameState = new GameState(platformManager.getCurrPlatform().getX(),platformManager.getCurrPlatform().getY(),platformManager.getCurrPlatform().getWidth(),platformManager.getNextPlatform().getX(),platformManager.getNextPlatform().getY(),platformManager.getNextPlatform().getWidth(), scoreLabel.getScore(),hero.getHeroCoordinates(),cherry.getCherryCoordinates(),cherryLabel.getScore());
            HighScore high = new HighScore(scoreLabel.getScore());

            oos.writeObject(high);


        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void savelastscore(ScoreLabel scoreLabel){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savelastscore.ser"))){

//            gameState = new GameState(platformManager.getCurrPlatform().getX(),platformManager.getCurrPlatform().getY(),platformManager.getCurrPlatform().getWidth(),platformManager.getNextPlatform().getX(),platformManager.getNextPlatform().getY(),platformManager.getNextPlatform().getWidth(), scoreLabel.getScore(),hero.getHeroCoordinates(),cherry.getCherryCoordinates(),cherryLabel.getScore());
            HighScore high = new HighScore(scoreLabel.getScore());

                    oos.writeObject(high);


        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void savelastscore2(ScoreLabel scoreLabel,int count){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savelastscore2.ser"))){

//            gameState = new GameState(platformManager.getCurrPlatform().getX(),platformManager.getCurrPlatform().getY(),platformManager.getCurrPlatform().getWidth(),platformManager.getNextPlatform().getX(),platformManager.getNextPlatform().getY(),platformManager.getNextPlatform().getWidth(), scoreLabel.getScore(),hero.getHeroCoordinates(),cherry.getCherryCoordinates(),cherryLabel.getScore());
            if(count==0){
                HighScore high = new HighScore(loadlastscore());
                oos.writeObject(high);
            }
            else{
                HighScore high = new HighScore(0);
                oos.writeObject(high);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveGameState(ScoreLabel scoreLabel,Hero hero,CherryLabel cherryLabel,Cherry cherry,Pane pane) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.ser"))) {
            gameState = GameState.getGameState(platformManager.getCurrPlatform().getX(),platformManager.getCurrPlatform().getY(),platformManager.getCurrPlatform().getWidth(),platformManager.getNextPlatform().getX(),platformManager.getNextPlatform().getY(),platformManager.getNextPlatform().getWidth(), scoreLabel.getScore(),hero.getHeroCoordinates(),cherry.getCherryCoordinates(),cherryLabel.getScore());
            oos.writeObject(gameState);
            Text bkwaas=new Text();
            bkwaas.setText("Progress Saved!");
            bkwaas.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
            bkwaas.setX(135);
            bkwaas.setY(280);
            bkwaas.setFill(Color.BLACK);
            pane.getChildren().add(bkwaas);
            Duration delay = Duration.seconds(2);
            KeyFrame keyFrame = new KeyFrame(delay, event -> pane.getChildren().remove(bkwaas));
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int loadhighscore(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savehighscore.ser"))) {
            HighScore high = (HighScore) ois.readObject();
               return high.getScore();


        } catch (IOException | ClassNotFoundException e) {
        }
        return 0;
    }
    public static int loadgamescore(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegamescore.ser"))) {
            HighScore high = (HighScore) ois.readObject();
            if (high != null) {
                return high.getScore();

            }
        } catch (IOException | ClassNotFoundException e) {
        }
        return 0;
    }
    public static int loadlastscore(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savelastscore.ser"))) {
            HighScore high = (HighScore) ois.readObject();
            if (high != null) {
                return high.getScore();

            }
        } catch (IOException | ClassNotFoundException e) {

        }
        return 0;
    }
    public static int loadlastscore2(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savelastscore2.ser"))) {
            HighScore high = (HighScore) ois.readObject();
            if (high != null) {
                return high.getScore();

            }
        } catch (IOException | ClassNotFoundException e) {
        }
        return 0;
    }

    public void loadeachstage(ScoreLabel scoreLabel,Hero hero,CherryLabel cherryLabel,Cherry cherry,Pane pane,PlatformManager plat){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegamestage.ser"))) {
            gameState = (GameState) ois.readObject();
            if (gameState != null) {
                pane.getChildren().remove(platformManager.getCurrPlatform().getRectangle());
                pane.getChildren().remove(platformManager.getNextPlatform().getRectangle());
                plat.removePlatforms();

                Platform currPlatform = new Platform(gameState.getCurrPlatformX(), gameState.getCurrPlatformY(), gameState.getCurrPlatformWidth());
                Platform nextPlatform = new Platform(gameState.getNextPlatformX(), gameState.getNextPlatformY(), gameState.getNextPlatformWidth());

                platformManager.addPlatform(currPlatform);
                platformManager.addPlatform(nextPlatform);

                pane.getChildren().addAll(currPlatform.getRectangle(), nextPlatform.getRectangle());

                scoreLabel.updateScore(gameState.getScore());

                SerializablePoint heroCoordinates = gameState.getHeroCoordinates();
                hero.setHeroCoordinates(heroCoordinates.getX(),heroCoordinates.getY());
                System.out.println(hero.getHeroCoordinates().getX()+" "+ hero.getHeroCoordinates().getY());
                SerializablePoint cherryCoordinates = gameState.getCherryCoordinates();
                cherry.setCherryCoordinates(cherryCoordinates.getX(),cherryCoordinates.getY());
                pane.getChildren().remove(cherry.getImageView());
                pane.getChildren().add(cherry.getImageView());

                cherryLabel.updateScore(gameState.getCherryScore());

                Text loadMessage = new Text("Game progress loaded!");
                loadMessage.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
                loadMessage.setFill(Color.BLACK);
                loadMessage.setX(135);
                loadMessage.setY(280);
                pane.getChildren().add(loadMessage);
                Duration delay = Duration.seconds(2);
                KeyFrame keyFrame = new KeyFrame(delay, event -> pane.getChildren().remove(loadMessage));
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadGameState(ScoreLabel scoreLabel,Hero hero,CherryLabel cherryLabel,Cherry cherry,Pane pane,PlatformManager plat) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.ser"))) {
            gameState = (GameState) ois.readObject();

            if (gameState != null) {
                pane.getChildren().remove(platformManager.getCurrPlatform().getRectangle());
                pane.getChildren().remove(platformManager.getNextPlatform().getRectangle());
                plat.removePlatforms();
                Platform currPlatform = new Platform(gameState.getCurrPlatformX(), gameState.getCurrPlatformY(), gameState.getCurrPlatformWidth());
                Platform nextPlatform = new Platform(gameState.getNextPlatformX(), gameState.getNextPlatformY(), gameState.getNextPlatformWidth());

                plat.addPlatform(currPlatform);
                plat.addPlatform(nextPlatform);

                pane.getChildren().addAll(currPlatform.getRectangle(), nextPlatform.getRectangle());

                scoreLabel.updateScore(gameState.getScore());

                SerializablePoint heroCoordinates = gameState.getHeroCoordinates();
                hero.setHeroCoordinates(heroCoordinates.getX(),heroCoordinates.getY());

                SerializablePoint cherryCoordinates = gameState.getCherryCoordinates();
                cherry.setCherryCoordinates(cherryCoordinates.getX(),cherryCoordinates.getY());
                pane.getChildren().remove(cherry.getImageView());
                pane.getChildren().add(cherry.getImageView());

                cherryLabel.updateScore(gameState.getCherryScore());

                Text loadMessage = new Text("Game progress loaded!");
                loadMessage.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
                loadMessage.setFill(Color.BLACK);
                loadMessage.setX(135);
                loadMessage.setY(280);
                pane.getChildren().add(loadMessage);
                Duration delay = Duration.seconds(2);
                KeyFrame keyFrame = new KeyFrame(delay, event -> pane.getChildren().remove(loadMessage));
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();

            }
        } catch (IOException | ClassNotFoundException e) {

        }
    }
    private void loadCherryCountState(ScoreLabel scoreLabel,Hero hero,CherryLabel cherryLabel,Cherry cherry,Pane pane,PlatformManager plat) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.ser"))) {
            gameState = (GameState) ois.readObject();
            if (gameState != null) {
                cherryLabel.updateScore(gameState.getCherryScore());
            }
        } catch (IOException | ClassNotFoundException e) {

        }
    }
    private void loadHighestScoreAndPreviousScore(ScoreLabel scoreLabel,Hero hero,CherryLabel cherryLabel,Cherry cherry,Pane pane,PlatformManager plat) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.ser"))) {
            gameState = (GameState) ois.readObject();
            if (gameState != null) {
                cherryLabel.updateScore(gameState.getCherryScore());
            }
        } catch (IOException | ClassNotFoundException e) {

        }
    }
    public void creategamescreen(Stage window){
        Pane pane = new Pane();

        Text text = new Text();
        text.setText("STICK\nHERO");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 90));
        text.setX(50);
        text.setY(120);
        pane.getChildren().add(text);



        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Playbutton.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setX(130);
        imageView.setY(330);
        pane.getChildren().add(imageView);

        double buttonWidth = 70;
        double buttonHeight = 70;
        Button playButton = new Button();
        playButton.setMinSize(buttonWidth, buttonHeight);
        playButton.setStyle("-fx-shape: \"M50,0 A50,50 0 1,0 100,50 A50,50 0 1,0 0,50 Z\";");
        playButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-traversable: false;");
        playButton.setLayoutX(145);
        playButton.setLayoutY(345);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), imageView);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.setFromY(0);
        translateTransition.setToY(10);
        translateTransition.play();

        playButton.setOnAction(e -> {
            GameScreen gameScreen = new GameScreen();
            try {
                GameScreen.home=false;
                gameScreen.start(window);

            } catch (Exception ex) {

            }
        });
        pane.getChildren().add(playButton);



        BackgroundSize backgroundSize = new BackgroundSize(60, 60, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("background.jpeg.jpg")), 800, 600, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        pane.setBackground(new Background(backgroundImage));

        Image soundimage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pngimg.com - sound_PNG8 - Copy.png")));
        ImageView soundimageView = new ImageView(soundimage);
        soundimageView.setFitWidth(30);
        soundimageView.setFitHeight(30);
        soundimageView.setX(20);
        soundimageView.setY(600);
        pane.getChildren().add(soundimageView);

        Image heroimage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("stick-hero - Copy.png")));
        ImageView heroimageView = new ImageView(heroimage);
        heroimageView.setFitWidth(40);
        heroimageView.setFitHeight(40);
        heroimageView.setX(160);
        heroimageView.setY(530);
        pane.getChildren().add(heroimageView);

        Image pillar1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pillar1 - Copy.jpg")));
        ImageView pillar1View = new ImageView(pillar1);
        pillar1View.setFitWidth(100);
        pillar1View.setFitHeight(100);
        pillar1View.setX(130);
        pillar1View.setY(570);
        pane.getChildren().add(pillar1View);

        Image question = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pngwing.com (1) - Copy.png")));
        ImageView questionView = new ImageView(question);
        questionView.setFitWidth(40);
        questionView.setFitHeight(40);
        questionView.setX(15);
        questionView.setY(550);
        pane.getChildren().add(questionView);

       Scene  startScene2 = new Scene(pane, 360, 640);

        window.setScene(startScene2);
        window.setTitle("Stick Hero Game");
        window.show();
    }
}
class GameOver  {
    GameScreen game=new GameScreen();
    public static Scene createGameOverScene(Stage window,int cherrycount) {
        Pane gameoverpane = new Pane();
        Text text = new Text();
        text.setText("GAME OVER!");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 57));
        text.setX(-3);
        text.setY(120);

        gameoverpane.getChildren().add(text);
        Rectangle bestBackground = new Rectangle(30, 140, 300, 130);
        bestBackground.setFill(Color.WHITE);
        gameoverpane.getChildren().add(bestBackground);
        bestBackground.setHeight(bestBackground.getHeight() + 80);
        Text score = new Text();
        score.setText("LAST SCORE");
        score.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        score.setX(120);
        score.setY(160);

        gameoverpane.getChildren().add(score);

        Text zero = new Text();
        int last =GameScreen.loadlastscore2();
        zero.setText(Integer.toString(last));
        zero.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        zero.setX(170);
        zero.setY(200);

        gameoverpane.getChildren().add(zero);

        Text best = new Text();
        best.setText("BEST SCORE");
        best.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        best.setX(120);
        best.setY(220);

        gameoverpane.getChildren().add(best);

        Text six = new Text();
        int highscore=GameScreen.loadhighscore();
        six.setText(Integer.toString(highscore));
        six.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        six.setX(170);
        six.setY(260);

        gameoverpane.getChildren().add(six);

        Text bes = new Text();
        bes.setText("GAME SCORE");
        bes.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        bes.setX(120);
        bes.setY(280);

        gameoverpane.getChildren().add(bes);

        Text si = new Text();
        int high=GameScreen.loadgamescore();
        si.setText(Integer.toString(high));
        si.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        si.setX(170);
        si.setY(320);

        gameoverpane.getChildren().add(si);

        BackgroundSize backgroundSize = new BackgroundSize(60, 60, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(GameOver.class.getResourceAsStream("background.jpeg.jpg")), 800, 600, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        gameoverpane.setBackground(new Background(backgroundImage));

        Image soundimage = new Image(Objects.requireNonNull(GameOver.class.getResourceAsStream("Home.png - Copy.png")));
        ImageView soundimageView = new ImageView(soundimage);
        soundimageView.setFitWidth(40);
        soundimageView.setFitHeight(40);
        soundimageView.setX(90);
        soundimageView.setY(455);
        gameoverpane.getChildren().add(soundimageView);
        double buttonWidth = 40;
        double buttonHeight = 40;
        Button HomeButton = new Button();
        HomeButton.setMinSize(buttonWidth, buttonHeight);
        HomeButton.setStyle(
                "-fx-shape: \"M50,0 A50,50 0 1,0 100,50 A50,50 0 1,0 0,50 Z\";" +
                        "-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-traversable: false;"
        );
        HomeButton.setLayoutX(90);
        HomeButton.setLayoutY(455);
        HomeButton.setOnAction(e -> {
            GameScreen gameScreen = new GameScreen();
            try {
                GameScreen.home2 = true;
                gameScreen.start(window);
            } catch (Exception ex) {

            }
        });
        gameoverpane.getChildren().add(HomeButton);


        Image heroimage = new Image(Objects.requireNonNull(GameOver.class.getResourceAsStream("Stats.png - Copy.png")));
        ImageView heroimageView = new ImageView(heroimage);
        heroimageView.setFitWidth(50);
        heroimageView.setFitHeight(50);
        heroimageView.setX(150);
        heroimageView.setY(450);
        gameoverpane.getChildren().add(heroimageView);

        Image question = new Image(Objects.requireNonNull(GameOver.class.getResourceAsStream("revive.jpg")));
        ImageView questionView = new ImageView(question);
        questionView.setFitWidth(50);
        questionView.setFitHeight(50);
        questionView.setX(200);
        questionView.setY(455);
        gameoverpane.getChildren().add(questionView);
        buttonWidth = 40;
        buttonHeight = 40;
        Button reviveButton = new Button();
        reviveButton.setMinSize(buttonWidth, buttonHeight);
        reviveButton.setStyle("-fx-shape: \"M50,0 A50,50 0 1,0 100,50 A50,50 0 1,0 0,50 Z\";");
        reviveButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-traversable: false;");
        reviveButton.setLayoutX(200);
        reviveButton.setLayoutY(455);
        reviveButton.setOnAction(e -> {
           if(cherrycount>=2) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Revive Confirmation");
                alert.setHeaderText("Reviving reguires 2 cherries");
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == buttonTypeYes) {
                    System.out.println("Reviving...");
                    GameScreen gameScreen = new GameScreen();
                    try {
                        GameScreen.revived = true;
                        gameScreen.start(window);
                    } catch (Exception ex) {

                    }
                } else {
                    System.out.println("Revive canceled.");
                }
            }
           else{
               Rectangle background = new Rectangle(250, 150);
               background.setFill(Color.LIGHTGRAY);
               background.setArcWidth(10);
               background.setArcHeight(10);
               background.setY(140);
               background.setX(80);
               Label messageLabel = new Label("Not Enough\nCherries");
               messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
               messageLabel.setTextFill(Color.BLACK);
               messageLabel.setLayoutX(150);
               messageLabel.setLayoutY(160);
               gameoverpane.getChildren().addAll(background,messageLabel);
               Duration showDuration = Duration.seconds(1);
               Duration hideDuration = Duration.seconds(1);
               KeyFrame showKeyFrame = new KeyFrame(showDuration, event -> {
                   background.setVisible(true);
                   messageLabel.setVisible(true);
               });
               KeyFrame hideKeyFrame = new KeyFrame(showDuration.add(hideDuration), event -> {

                   background.setVisible(false);
                   messageLabel.setVisible(false);
               });
               Timeline timeline = new Timeline(showKeyFrame, hideKeyFrame);
               timeline.play();
              // gameoverpane.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 20;");
           }
        });

        gameoverpane.getChildren().add(reviveButton);

        Image restart = new Image(Objects.requireNonNull(GameOver.class.getResourceAsStream("Restart.png - Copy.png")));
        ImageView restartView = new ImageView(restart);
        restartView.setFitWidth(40);
        restartView.setFitHeight(40);
        restartView.setX(255);
        restartView.setY(455);
        gameoverpane.getChildren().add(restartView);
        Button restartButton = new Button();
        restartButton.setMinSize(buttonWidth, buttonHeight);
        restartButton.setStyle("-fx-shape: \"M50,0 A50,50 0 1,0 100,50 A50,50 0 1,0 0,50 Z\";");
        restartButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-traversable: false;");
        restartButton.setLayoutX(255);
        restartButton.setLayoutY(455);
        restartButton.setOnAction(e -> {
            GameScreen gameScreen = new GameScreen();
            try {
                gameScreen.start(window);
            } catch (Exception ex) {

            }
        });
        gameoverpane.getChildren().add(restartButton);
        return new Scene(gameoverpane, 360, 640);
    }
}
