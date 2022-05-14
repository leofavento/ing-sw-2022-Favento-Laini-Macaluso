package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public enum CommunicationMessage implements Message {
    PING("Ping!"),
    ENTER_NICKNAME("Please enter your nickname: "),
    SUCCESS("Success"),
    END_MOVE_1("Move 1 is ended"),
    UNIFIED_ISLANDS("Some islands have been unified"),
    NO_ENTRY_TILE_ON_ISLAND("Island was not resolved because of No Entry Tile"),
    END_MOVE_2("Move 2 is ended"),
    NEW_GAME("Please enter your game preferences (number of players and gamemode): "),
    CLOSING_GAME("The game is closed");


    private final String message;

    CommunicationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}