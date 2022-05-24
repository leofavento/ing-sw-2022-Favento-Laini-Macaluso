package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.LoginMessage;

import java.io.IOException;
import java.util.Scanner;

public class NicknameState implements State {
    private String nickname;
    private final CLI cli;

    public NicknameState(CLI cli) {
        this.cli = cli;
    }

    private boolean validNickname(String nickname) {
        for (int i = 0; i < nickname.length(); i++) {
            char c = nickname.charAt(i);

            if (Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);

        boolean firstLoop = true;
        while (!cli.isSuccess()) {
            if (!firstLoop) {
                System.out.println("Enter your nickname: ");
            }
            in.reset();
            nickname = in.next();
            if (!validNickname(nickname)) {
                System.out.println("The nickname entered is not valid.");
            } else {
                cli.getClient().sendMessage(new LoginMessage(nickname));
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            firstLoop = false;
        }
        if (cli.isSuccess()) {
            cli.getClient().setNickname(nickname);
            cli.setSuccess(false);
            cli.setGameState(new LobbyState(cli));
        }
    }
}