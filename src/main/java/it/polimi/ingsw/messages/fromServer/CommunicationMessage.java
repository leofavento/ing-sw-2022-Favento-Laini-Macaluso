package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;

/**
 * Message sent by server to inform the player about an event picked from an enum.
 */

public enum CommunicationMessage implements FromServerMessage {
    PING("Ping!"),
    ENTER_NICKNAME("Please enter your nickname: "),
    SUCCESS("Success"),
    STUDENT_MOVED("The student has been moved."),
    UNIFIED_ISLANDS("Some islands have been unified"),
    NO_CHANGES("Influence resulted in a draw. No tower change."),
    NO_ENTRY_TILE_ON_ISLAND("Island was not resolved because of No Entry Tile"),
    LAST_ROUND("This will be the last round of this game"),
    CLOSING_GAME("The game is closed"),
    HOST_LEFT("The host left, you're being disconnected...");


    private final String message;

    CommunicationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}