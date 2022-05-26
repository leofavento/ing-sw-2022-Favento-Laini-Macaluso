package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

/**
 * Message sent by client when a message has been acknowledged.
 */

public class Ack implements Message {
    public Ack() {

    }

    public String getMessage() {
        return "Ack";
    }
}