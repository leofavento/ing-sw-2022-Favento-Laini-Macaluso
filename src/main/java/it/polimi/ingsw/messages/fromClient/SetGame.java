package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

public class SetGame implements Message {
    private final int numberOfPlayers;
    private final boolean expertMode;

    public SetGame(int numberOfPlayers, boolean expertMode){
        this.numberOfPlayers = numberOfPlayers;
        this.expertMode = expertMode;
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public boolean getExpertMode(){
        return expertMode;
    }

}