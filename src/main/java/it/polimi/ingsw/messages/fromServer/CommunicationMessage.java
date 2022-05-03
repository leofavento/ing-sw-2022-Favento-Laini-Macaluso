package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public enum CommunicationMessage implements Message {
    ENTER_NICKNAME("Please enter your nickname: "),
    TAKEN_NICKNAME("This nickname is already taken, please choose another one.");

    private final String message;

    CommunicationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
