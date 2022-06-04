package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

/**
 * Message sent by the server to the player to communicate the start of his round
 */
public class StartOfPlayerRound implements FromServerMessage {
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

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
