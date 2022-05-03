package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class StartOfPlayerRound implements Message {
    private final int roundNumber;
    private final String nickname;

    public StartOfPlayerRound(int roundNumber, String nickname){
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
