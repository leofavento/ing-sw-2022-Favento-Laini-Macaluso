package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

public class JoinAvailableGame implements Message {
    private final int gameID;

    public JoinAvailableGame(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
