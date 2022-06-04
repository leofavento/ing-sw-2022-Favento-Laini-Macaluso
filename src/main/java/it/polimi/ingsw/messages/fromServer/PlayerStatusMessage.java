package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.player.PlayerStatus;

/**
 * Message sent by the server to communicate the status of a player.
 */
public class PlayerStatusMessage implements FromServerMessage {
    private final PlayerStatus playerStatus;

    public PlayerStatusMessage(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
