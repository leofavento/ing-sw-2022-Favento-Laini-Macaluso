package it.polimi.ingsw.messages;

import java.io.Serializable;

public class LoginMessage implements Serializable {
    private final String nickname;

    public LoginMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
