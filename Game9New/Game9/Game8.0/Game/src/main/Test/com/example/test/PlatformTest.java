package com.example.test;

import com.example.game.Platform;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlatformTest {
    @Test
    void testPlatformInitialization() {
        double initialX = 50;
        double initialY = 100;
        double initialWidth = 200;
        Platform platform = new Platform(initialX, initialY, initialWidth);
        assertNotNull(platform, "Platform should be initialized");
        assertEquals(initialX, platform.getX(), "Platform X coordinate should be initialized");
        assertEquals(initialY, platform.getY(), "Platform Y coordinate should be initialized");
        assertEquals(initialWidth, platform.getWidth(), "Platform width should be initialized");
        assertNotNull(platform.getRectangle(), "Platform rectangle should be initialized");
        assertEquals(initialX, platform.getRectangle().getX(), "Platform rectangle X coordinate should be initialized");
        assertEquals(initialY, platform.getRectangle().getY(), "Platform rectangle Y coordinate should be initialized");
        assertEquals(initialWidth, platform.getRectangle().getWidth(), "Platform rectangle width should be initialized");
        assertEquals(170, platform.getRectangle().getHeight(), "Platform rectangle height should be initialized");
        assertEquals(Color.BLACK, platform.getRectangle().getFill(), "Platform rectangle fill color should be BLACK");
    }

    @Test
    void testGetX() {
        double initialX = 50;
        double initialY = 100;
        double initialWidth = 200;
        Platform platform = new Platform(initialX, initialY, initialWidth);
        double resultX = platform.getX();
        assertEquals(initialX, resultX, "getX should return the correct X coordinate");
    }

    @Test
    void testGetY() {
        double initialX = 50;
        double initialY = 100;
        double initialWidth = 200;
        Platform platform = new Platform(initialX, initialY, initialWidth);
        double resultY = platform.getY();
        assertEquals(initialY, resultY, "getY should return the correct Y coordinate");
    }

    @Test
    void testGetWidth() {
        double initialX = 50;
        double initialY = 100;
        double initialWidth = 200;
        Platform platform = new Platform(initialX, initialY, initialWidth);
        double resultWidth = platform.getWidth();
        assertEquals(initialWidth, resultWidth, "getWidth should return the correct width");
    }
}
