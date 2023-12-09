package com.example.game;

import java.io.Serializable;
import java.util.Objects;

public class HighScore implements Serializable {
    private static final long serialVersionUID = 1L;


    private int score;

    @Override
    public String toString() {
        return "HighScore{" +
                "score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HighScore highScore)) return false;
        return score == highScore.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }

    public HighScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
