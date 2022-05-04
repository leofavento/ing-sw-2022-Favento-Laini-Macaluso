package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class WhereToMove implements Message {
    private final boolean diningRoom;
    private final int islands;

    public WhereToMove(boolean diningRoom, int islands){
        this.diningRoom = diningRoom;
        this.islands = islands;
    }

    public boolean isDiningRoom() {
        return diningRoom;
    }

    public int getIslands() {
        return islands;
    }
}
