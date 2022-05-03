package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameInfo;

public class JoinAlreadyExistingGame implements Message {

    private int gameID;
    private int numOfWaitingPlayers;
    private int numOfTotalPlayers;
    private boolean expertGame;


    public JoinAlreadyExistingGame(int gameID, int numOfWaitingPlayers, int numOfTotalPlayers, boolean expertGame){
        this.gameID = gameID;
        this.numOfWaitingPlayers = numOfWaitingPlayers;
        this.numOfTotalPlayers = numOfTotalPlayers;
        this.expertGame = expertGame;


    }

    GameInfo gameInfo = new GameInfo(gameID, numOfWaitingPlayers, numOfTotalPlayers, expertGame);

    public GameInfo getGameInfo(){
        return gameInfo;
    }

}