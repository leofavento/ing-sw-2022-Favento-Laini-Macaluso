package it.polimi.ingsw.messages;

public enum CommunicationMessage implements Message {
    TAKEN_NICKNAME("This nickname is already taken, please choose another one.");

    private final String message;

    CommunicationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
