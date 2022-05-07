package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.StudentDeposit;
import it.polimi.ingsw.model.player.DiningRoom;

public class ChosenDestination implements Message {
    private final DiningRoom diningRoom;
    private final Island island;

    public ChosenDestination(DiningRoom diningRoom) {
        this.diningRoom = diningRoom;
        this.island = null;
    }

    public ChosenDestination(Island island) {
        this.island = island;
        diningRoom = null;
    }

    public StudentDeposit getDestination() {
        if (island == null) {
            return diningRoom;
        } else {
            return island;
        }
    }
}
