package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class CommunicateWinner implements Message {
    private final String nickname;
    private final String winReason;

    public CommunicateWinner(String nickname, String winReason){
        this.nickname = nickname;
        this.winReason = winReason;
    }

    public String getNickname() {
        return nickname;
    }

    public String getWinReason() {
        return winReason;
    }
}
