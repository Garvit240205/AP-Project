package com.example.game;

import java.util.LinkedList;
import java.util.Queue;

public class CherryManager {
    private  Queue<Cherry> cherries;

    public CherryManager() {
        cherries = new LinkedList<>();
    }

    public void addCherry(Cherry cherry) {
        cherries.add(cherry);
    }

    public Cherry getCurrCherry() {
        return cherries.peek();
    }

    public void removeCurrCherry() {
        cherries.poll();
    }
}

