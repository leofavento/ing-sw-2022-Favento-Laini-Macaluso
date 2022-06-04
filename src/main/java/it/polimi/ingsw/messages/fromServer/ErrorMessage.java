package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

/**
 * Message sent by the server to communicate an error selected from an enum.
 */

public enum ErrorMessage implements FromServerMessage {
    TAKEN_NICKNAME("This nickname is already taken, please choose another one."),
    WRONG_TURN("This is not your turn."),
    ALREADY_RECEIVED("This message was already received."),
    NICKNAME_NOT_VALID("The selected nickname is not valid"),
    INVALID_SETTINGS("The desired settings are not valid"),
    FULL_GAME("The selected game is already full"),
    TOWER_NOT_AVAILABLE("The chosen tower color is not available"),
    WIZARD_NOT_AVAILABLE("The chosen wizard is not available"),
    STUDENT_NOT_AVAILABLE("The chosen student is not available"),
    FULL_DINING_ROOM("The dining room is full for the selected color"),
    INVALID_ASSISTANT("The chosen assistant has already been played by another player"),
    UNAVAILABLE_ASSISTANT("You have already used this assistant in a previous turn."),
    INVALID_INPUT("Invalid input"),
    NOT_ENOUGH_COINS("You don't have enough coins to enable this character."),
    EMPTY_CLOUD("You can't select an empty cloud."),
    ZERO_NO_ENTRY_TILES_LEFT("There are zero No Entry Tiles left on the Character5 card"),
    INVALID_ACTION("There's a time and a place for everything, but not now."),
    ALREADY_PLAYED_CHARACTER("You have already played a Character in this turn.");

    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}