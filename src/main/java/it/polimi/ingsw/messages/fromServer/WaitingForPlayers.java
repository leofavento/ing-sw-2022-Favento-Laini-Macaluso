package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;

public class WaitingForPlayers implements FromServerMessage {
    int gameID;

    public WaitingForPlayers(int gameID) {
        this.gameID = gameID;
    }

    public String getMessage() {
        return String.format("The game (ID = %d) has been successfully created.", gameID);
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
