package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.gameStates.NicknameState;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.*;

public class MessageReceiver {
    private final CLI cli;
    private final Client client;

    public MessageReceiver(CLI cli, Client client) {
        this.cli = cli;
        this.client = client;
    }

    public void receiveMessage(Message message) {
        if (message instanceof CommunicationMessage) {
            receiveMessage((CommunicationMessage) message);
        } else if (message instanceof ErrorMessage) {
            receiveMessage((ErrorMessage) message);
        } else if (message instanceof AvailableGames) {
            receiveMessage((AvailableGames) message);
        } else if (message instanceof JoinAlreadyExistingGame) {
            receiveMessage((JoinAlreadyExistingGame) message);
        } else if (message instanceof WaitingForPlayers) {
            receiveMessage((WaitingForPlayers) message);
        }
    }

    public void receiveMessage(CommunicationMessage message) {
        if (message == CommunicationMessage.SUCCESS) {
            cli.setSuccess(true);
            synchronized (cli.getGameState()) {
                cli.getGameState().notifyAll();
            }
        } else if (message == CommunicationMessage.ENTER_NICKNAME) {
            System.out.println(message.getMessage());
            cli.setGameState(new NicknameState(cli));
        }
    }

    public void receiveMessage(ErrorMessage message) {
        System.out.println(message.getMessage());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(AvailableGames message) {
        cli.setAvailableGames(message.getAvailableGames());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(JoinAlreadyExistingGame message) {
        cli.setSuccess(true);
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(WaitingForPlayers message) {
        System.out.println(message.getMessage());
        cli.setSuccess(true);
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }
}
