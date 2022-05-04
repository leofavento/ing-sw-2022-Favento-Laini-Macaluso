package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInfo;

public class MatchStarted implements Message {
    private final GameInfo gameInfo;

    public MatchStarted(GameInfo gameInfo){
        this.gameInfo = gameInfo;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }
}
