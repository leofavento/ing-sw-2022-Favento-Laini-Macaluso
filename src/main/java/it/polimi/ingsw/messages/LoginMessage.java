package it.polimi.ingsw.messages;

public class LoginMessage implements Message {
    private final String nickname;

    public LoginMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
