package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Tower;

import java.util.HashMap;
import java.util.Map;

/**
 * Message sent by the server so that the client can see which tower colors are still available.
 */
public class AvailableTowers implements FromServerMessage {
    private final HashMap<Tower, Integer> availableTowers;

    public AvailableTowers(HashMap<Tower, Integer> availableTowers) {
        this.availableTowers = availableTowers;
    }

    public HashMap<Tower, Integer> getAvailableTowers() {
        return availableTowers;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
