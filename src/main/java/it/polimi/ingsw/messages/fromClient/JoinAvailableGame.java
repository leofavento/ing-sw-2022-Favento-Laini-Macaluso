package it.polimi.ingsw.messages.fromClient;

public class JoinAvailableGame {
    private final int gameID;

    public JoinAvailableGame(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
