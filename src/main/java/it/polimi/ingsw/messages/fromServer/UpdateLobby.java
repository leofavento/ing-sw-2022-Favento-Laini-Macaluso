package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.GameInfo;

/**
 * Message sent by the server to communicate the lobby status after the last event.
 */
public class UpdateLobby implements FromServerMessage {
    private final GameInfo gameInfo;

    public UpdateLobby(int getGameID, int numOfWaitingPlayers, int numOfTotalPlayers, boolean isExpertGame){
        gameInfo = new GameInfo(getGameID,numOfWaitingPlayers,numOfTotalPlayers,isExpertGame);
    }

    public GameInfo getGameInfo(){
        return gameInfo;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
