package com.example.test;

import com.example.game.Cherry;
import com.example.game.SerializablePoint;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CherryTest {

    @Test
    void testCherryCoordinates() {
        Cherry cherry = new Cherry("cherry.png", 50, 75, 30, 30);

        cherry.setCherryCoordinates(100, 150);

        assertEquals(100, cherry.getImageView().getX(), "X coordinate should be updated");
        assertEquals(150, cherry.getImageView().getY(), "Y coordinate should be updated");
    }

    @Test
    void testGetCherryCoordinates() {
        Cherry cherry = new Cherry("cherry.png", 50, 75, 30, 30);

        SerializablePoint coordinates = cherry.getCherryCoordinates();

        assertEquals(50, coordinates.getX(), "X coordinate should match");
        assertEquals(75, coordinates.getY(), "Y coordinate should match");
    }

    @Test
    void testSetX() {
        Cherry cherry = new Cherry("cherry.png", 50, 75, 30, 30);

        cherry.setX(80);

        assertEquals(80, cherry.getImageView().getX(), "X coordinate should be updated");
    }

    @Test
    void testSetY() {
        Cherry cherry = new Cherry("cherry.png", 50, 75, 30, 30);

        cherry.setY(100);

        assertEquals(100, cherry.getImageView().getY(), "Y coordinate should be updated");
    }

    @Test
    void testDefaultConstructor() {
        Cherry cherry = new Cherry();

        ImageView imageView = cherry.getImageView();
        assertNotNull(imageView, "ImageView should not be null");
        assertEquals(22, imageView.getImage().getWidth(), "Default width should be 22");
        assertEquals(22, imageView.getImage().getHeight(), "Default height should be 22");
    }
}
