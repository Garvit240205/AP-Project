package com.example.test;

import com.example.game.Hero;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroTesting {

    @Test
    void testMoveUp() {
        //Image heroImageView = new Image((Objects.requireNonNull(getClass().getResourceAsStream("stick-hero - Copy.png"))));
        Hero hero = new Hero();
        hero.getHeroImage().setY(50);
        hero.moveup();
        double expectedY = 50;
        assertEquals(expectedY, hero.getHeroImage().getY(), "Hero should not move up ");
    }

    @Test
    void testMoveUpWhenMovingDown() {
       // Image heroImageView = new Image((Objects.requireNonNull(getClass().getResourceAsStream("stick-hero - Copy.png"))));
        Hero hero = new Hero() ;
        hero.getHeroImage().setY(100);
        hero.setMovingDown(true);
        hero.moveup();
        double expectedY = 50; //change this value to get the error mssg.
        assertEquals(expectedY,  hero.getHeroImage().getY(), "Hero should not move up when already moving down");
    }
}