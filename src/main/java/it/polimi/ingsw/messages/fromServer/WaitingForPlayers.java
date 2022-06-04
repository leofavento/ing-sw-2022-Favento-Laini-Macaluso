package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

/**
 * Message sent by the server when creating a new game. The server is waiting for other players to join the game.
 */

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
