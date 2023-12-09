package com.example.test;

import com.example.game.CherryLabel;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CherryLabelTest {

    @Test
    void testDefaultConstructor() {
        CherryLabel cherryLabel = new CherryLabel();

        assertNotNull(cherryLabel.getScoreText(), "Score text should not be null");
        assertEquals("0", cherryLabel.getScoreText().getText(), "Initial score text should be 0");
        assertEquals(Font.font("Arial", FontWeight.BOLD, 25), cherryLabel.getScoreText().getFont(), "Font should match");
        assertEquals(Color.WHITE, cherryLabel.getScoreText().getFill(), "Text color should be white");
        assertTrue(cherryLabel.getScoreText().getX() == 305, "X position should be 305");
        assertTrue(cherryLabel.getScoreText().getY() == 36, "Y position should be 36");
    }

    @Test
    void testConstructorWithArgument() {
        CherryLabel cherryLabel = new CherryLabel(1);

        assertNotNull(cherryLabel.getScoreText(), "Score text should not be null");
        assertEquals("0", cherryLabel.getScoreText().getText(), "Initial score text should be 0");
        assertEquals(Font.font("Arial", FontWeight.BOLD, 20), cherryLabel.getScoreText().getFont(), "Font should match");
        assertEquals(Color.WHITE, cherryLabel.getScoreText().getFill(), "Text color should be white");
        assertTrue(cherryLabel.getScoreText().getX() == 310, "X position should be 310");
        assertTrue(cherryLabel.getScoreText().getY() == 30, "Y position should be 30");
    }

    @Test
    void testUpdateScore() {
        CherryLabel cherryLabel = new CherryLabel();
        cherryLabel.updateScore(10);

        assertEquals("10", cherryLabel.getScoreText().getText(), "Score text should be updated");
    }

    @Test
    void testGetScore() {
        CherryLabel cherryLabel = new CherryLabel();
        cherryLabel.updateScore(10);

        int score = cherryLabel.getScore();
        assertEquals(10, score, "Score should match");
    }

    @Test
    void testGetBackground() {
        CherryLabel cherryLabel = new CherryLabel();

        assertNotNull(cherryLabel.getBackground(), "Background should not be null");
    }
}
