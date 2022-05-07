package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.player.PlayerStatus;

public class PlayerStatusMessage implements Message {
    private final PlayerStatus playerStatus;

    public PlayerStatusMessage(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }
}
