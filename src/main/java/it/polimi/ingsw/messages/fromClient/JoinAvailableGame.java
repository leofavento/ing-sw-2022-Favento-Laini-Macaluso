package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

/**
 * Message sent by client communicating the intention to join an already created game.
 */

public class JoinAvailableGame implements Message {
    private final int gameID;

    public JoinAvailableGame(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
