package com.example.game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PlatformManager implements Serializable {
    boolean platformShifted = false;
    private static final long serialVersionUID = 1L;
    private LinkedList<Platform> platforms = new LinkedList<>();

    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    public Platform removeOldestPlatform() {
        return platforms.removeFirst();
    }
    public void removePlatforms(){
        platforms.clear();
    }

    public Platform getNextPlatform() {
        if (platforms != null && !platforms.isEmpty()) {
            return platforms.get(1);
        }
        else {
            return new Platform(100, 480, 30);
        }
    }

    public Platform getCurrPlatform() {
        if (platforms != null && !platforms.isEmpty()) {
            return platforms.get(0);
        } else {
            return new Platform(0, 480, 50);
        }
    }


    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void movePlatformsLeft() {
        for (Platform platform : platforms) {
            platform.getRectangle().setX(0);
        }
    }

    public void resetHasPlatformsBeenShiftedFlag() {
        platformShifted = false;
    }

    public void makeTrueFlag() {
        platformShifted = true;
    }

    public boolean hasPlatformsBeenShifted() {
        return platformShifted;
    }
}
