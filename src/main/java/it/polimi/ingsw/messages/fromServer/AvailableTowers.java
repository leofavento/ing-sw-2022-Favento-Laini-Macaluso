package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Tower;

import java.util.HashMap;
import java.util.Map;

public class AvailableTowers implements Message {
    private final HashMap<Tower, Integer> availableTowers;

    public AvailableTowers(HashMap<Tower, Integer> availableTowers) {
        this.availableTowers = availableTowers;
    }

    public HashMap<Tower, Integer> getAvailableTowers() {
        return availableTowers;
    }
}
