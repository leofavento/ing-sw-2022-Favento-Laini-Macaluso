package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public enum ErrorMessage implements Message {
    NICKNAME_NOT_VALID("The selected nickname is not valid");

    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}