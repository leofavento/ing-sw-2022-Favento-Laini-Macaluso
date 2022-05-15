package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

public class Ack implements Message {
    String nicknameSender;

    public Ack(String nickname) {
        nicknameSender = nickname;
    }

    public String getMessage() {
        return "Ack";
    }

    public String getSender() {
        return nicknameSender;
    }
}