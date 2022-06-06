package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

/**
 * Message sent by the server so that the client can see which tower colors are still available.
 */
public class AvailableTowers implements FromServerMessage {
    private final HashMap<Tower, Integer> availableTowers;
    private final EnumMap<Tower, ArrayList<Player>> currentTeams;

    public AvailableTowers(HashMap<Tower, Integer> availableTowers, EnumMap<Tower, ArrayList<Player>> currentTeams) {
        this.availableTowers = availableTowers;
        this.currentTeams = currentTeams;
    }

    public HashMap<Tower, Integer> getAvailableTowers() {
        return availableTowers;
    }

    public EnumMap<Tower, ArrayList<Player>> getCurrentTeams() {
        return currentTeams;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
