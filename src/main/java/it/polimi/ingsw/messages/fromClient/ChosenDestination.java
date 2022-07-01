package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
/**
 * Message sent by the client to select the destination from the list provided by the server.
 */

public class ChosenDestination implements Message {
    private final int destination;

    public ChosenDestination(int destination) {
        this.destination = destination;
    }

    public int getDestination() {
        return destination;
    }
}
