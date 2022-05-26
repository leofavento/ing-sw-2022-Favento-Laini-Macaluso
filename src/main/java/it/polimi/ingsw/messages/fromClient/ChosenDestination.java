package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.StudentDeposit;
import it.polimi.ingsw.model.player.DiningRoom;

public class ChosenDestination implements Message {
    private final int destination;

    public ChosenDestination(int destination) {
        this.destination = destination;
    }

    public int getDestination() {
        return destination;
    }
}
