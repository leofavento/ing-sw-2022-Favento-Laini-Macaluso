package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

/**
 * Message sent by the server to communicate that a player has disconnected from the game.
 */
public class PlayerDisconnected implements FromServerMessage {

    private final String nickname;

    public PlayerDisconnected(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
