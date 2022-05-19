package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public enum ErrorMessage implements Message {
    TAKEN_NICKNAME("This nickname is already taken, please choose another one."),
    WRONG_TURN("This is not your turn."),
    ALREADY_RECEIVED("This message was already received."),
    NICKNAME_NOT_VALID("The selected nickname is not valid"),
    INVALID_SETTINGS("The desired settings are not valid"),
    FULL_GAME("The selected game is already full"),
    TOWER_NOT_AVAILABLE("The chosen tower color is not available"),
    WIZARD_NOT_AVAILABLE("The chosen wizard is not available"),
    STUDENT_NOT_AVAILABLE("The chosen tower color is not available"),
    FULL_DINING_ROOM("The dining room is full for the selected color"),
    INVALID_ASSISTANT("The chosen assistant has already been played by another player"),
    UNAVAILABLE_ASSISTANT("You have already used this assistant in a previous turn."),
    INVALID_INPUT("Invalid input"),
    INVALID_ACTION("There's a time and a place for everything, but not now.");

    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}