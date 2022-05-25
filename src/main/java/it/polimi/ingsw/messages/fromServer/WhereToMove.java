package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.player.DiningRoom;

import java.util.ArrayList;

public class WhereToMove implements FromServerMessage {
    private final DiningRoom diningRoom;
    private final ArrayList<Island> islands;

    public WhereToMove(DiningRoom diningRoom, ArrayList<Island> islands){
        this.diningRoom = diningRoom;
        this.islands = islands;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
