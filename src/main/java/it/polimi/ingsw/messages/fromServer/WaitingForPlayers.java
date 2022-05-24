package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class WaitingForPlayers implements Message {
    int gameID;

    public WaitingForPlayers(int gameID) {
        this.gameID = gameID;
    }

    public String getMessage() {
        return String.format("The game (ID = %d) has been successfully created.\n" +
                "Waiting for other players to join the game...", gameID);
    }
}
