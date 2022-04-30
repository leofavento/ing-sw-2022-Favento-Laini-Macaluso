package it.polimi.ingsw.messages;

import java.io.Serializable;

public enum CommunicationMessage implements Serializable {
    TAKEN_NICKNAME("This nickname is already taken, please choose another one.");

    private final String message;

    CommunicationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
