package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

/**
 * Message sent by the client after establishing a connection to create player session.
 */

public class LoginMessage implements Message {
    private final String nickname;

    public LoginMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
