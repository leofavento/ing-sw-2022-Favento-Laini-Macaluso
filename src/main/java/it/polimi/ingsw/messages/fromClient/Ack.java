package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

public class Ack implements Message {
    public Ack() {

    }

    public String getMessage() {
        return "Ack";
    }
}