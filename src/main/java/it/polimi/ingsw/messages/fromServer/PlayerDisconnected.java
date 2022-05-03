package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class PlayerDisconnected implements Message {

    private final String nickname;

    public PlayerDisconnected(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }
}
