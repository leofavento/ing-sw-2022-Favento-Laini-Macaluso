package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

/**
 * Message sent by the client to the server communicating the parameters for the creation of a new game.
 */

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