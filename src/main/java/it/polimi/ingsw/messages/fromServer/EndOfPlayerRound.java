package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class EndOfPlayerRound implements Message {
    private final int roundNumber;
    private final String nickname;

    public EndOfPlayerRound(int roundNumber, String nickname){
        this.roundNumber = roundNumber;
        this.nickname = nickname;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getNickname() {
        return nickname;
    }
}
