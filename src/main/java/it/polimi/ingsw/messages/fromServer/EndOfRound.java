package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class EndOfRound implements Message {
    private final int roundNumber;

    public EndOfRound(int roundNumber){
        this.roundNumber = roundNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
