package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Tower;

import java.util.Map;

public class AvailableTowers implements Message {
    private final Map<Tower, Integer> availableTowers;

    public AvailableTowers(Map<Tower, Integer> availableTowers) {
        this.availableTowers = availableTowers;
    }

    public int getAvailableForTower(Tower color) {
        return availableTowers.get(color);
    }
}
