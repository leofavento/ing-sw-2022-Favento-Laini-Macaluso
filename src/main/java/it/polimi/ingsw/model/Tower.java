package it.polimi.ingsw.model;

import java.util.ArrayList;

public enum Tower {
    BLACK,
    WHITE,
    GREY;

    private static final ArrayList<Tower> availableTowers = new ArrayList<>();

    public static void reset() {
        availableTowers.clear();
        availableTowers.add(BLACK);
        availableTowers.add(WHITE);
        availableTowers.add(GREY);
    }

    public static void choose(Tower tower) {
        availableTowers.remove(tower);
    }

    public static ArrayList<Tower> getAvailableTowers() {
        return availableTowers;
    }
}
