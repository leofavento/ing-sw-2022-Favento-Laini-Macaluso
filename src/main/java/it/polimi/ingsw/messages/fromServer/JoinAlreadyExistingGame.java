package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInfo;

public class JoinAlreadyExistingGame implements Message {

    private GameInfo gameInfo;


    public JoinAlreadyExistingGame(){
        gameInfo = new GameInfo(gameInfo.getGameID(), gameInfo.getNumOfWaitingPlayers(), gameInfo.getNumOfTotalPlayers(), gameInfo.isExpertGame());


    }


    public GameInfo getGameInfo(){
        return gameInfo;
    }

}