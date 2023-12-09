package com.example.test;

import com.example.game.HighScore;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HighScoreTest {

    @Test
    void testGetScore() {
        int initialScore = 100;
        HighScore highScore = new HighScore(initialScore);
        int retrievedScore = highScore.getScore();
        assertEquals(initialScore, retrievedScore, "Initial score should be equal to retrieved score");
    }

    @Test
    void testSetScore() {
        int initialScore = 100;
        int newScore = 150;
        HighScore highScore = new HighScore(initialScore);
        highScore.setScore(newScore);
        int updatedScore = highScore.getScore();
        assertEquals(newScore, updatedScore, "Score should be updated to the new value");
    }
}
