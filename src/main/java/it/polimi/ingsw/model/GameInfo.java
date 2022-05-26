package it.polimi.ingsw.model;

import java.io.Serializable;

public class GameInfo implements Serializable {
    private final int gameID;
    private int numOfWaitingPlayers;
    private final int numOfTotalPlayers;
    private final boolean expertGame;
    private static final long serialVersionUID = -1285064980659081382L;

    public GameInfo(int gameID, int numOfWaitingPlayers, int numOfTotalPlayers, boolean expertGame) {
        this.gameID = gameID;
        this.numOfWaitingPlayers = numOfWaitingPlayers;
        this.numOfTotalPlayers = numOfTotalPlayers;
        this.expertGame = expertGame;
    }

    public int getGameID() {
        return gameID;
    }

    public int getNumOfWaitingPlayers() {
        return numOfWaitingPlayers;
    }

    public int getNumOfTotalPlayers() {
        return numOfTotalPlayers;
    }

    public boolean isExpertGame() {
        return expertGame;
    }

    public void setNumOfWaitingPlayers(int numOfWaitingPlayers) {
        this.numOfWaitingPlayers = numOfWaitingPlayers;
    }
}
