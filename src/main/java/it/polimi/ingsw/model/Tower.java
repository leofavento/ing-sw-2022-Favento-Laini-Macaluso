package it.polimi.ingsw.model;

import java.util.ArrayList;

public enum Tower {
    BLACK,
    WHITE,
    GREY;

    //private final ArrayList<Tower> availableTowers = new ArrayList<>();
    private final ArrayList<Player> teamComponents = new ArrayList<>();

    /*
    public void reset() {
        availableTowers.clear();
        availableTowers.add(BLACK);
        availableTowers.add(WHITE);
        availableTowers.add(GREY);
    }

    public void choose(Tower tower) {
        availableTowers.remove(tower);
    }

    public ArrayList<Tower> getAvailableTowers() {
        return availableTowers;
    }
    */

    public ArrayList<Player> getTeamComponents() {
        return teamComponents;
    }

    public void addTeamComponent(Player p) {
        teamComponents.add(p);
    }
}
