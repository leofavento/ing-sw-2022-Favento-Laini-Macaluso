package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.gameStates.GameSetupState;
import it.polimi.ingsw.client.cli.gameStates.NicknameState;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Tower;

import java.util.HashMap;

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
        } else if (message instanceof UpdateLobby) {
            receiveMessage((UpdateLobby) message);
        } else if (message instanceof AvailableTowers) {
            receiveMessage((AvailableTowers) message);
        }
    }

    public void receiveMessage(CommunicationMessage message) {
        if (message != CommunicationMessage.SUCCESS) {
            System.out.println(message.getMessage());
        }

        if (message == CommunicationMessage.SUCCESS) {
            cli.setSuccess(true);
            synchronized (cli.getGameState()) {
                cli.getGameState().notifyAll();
            }
        } else if (message == CommunicationMessage.ENTER_NICKNAME) {
            cli.setGameState(new NicknameState(cli));
        } else if (message == CommunicationMessage.NEW_GAME) {
            cli.setGameState(new GameSetupState(cli));
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

    public void receiveMessage(UpdateLobby message) {
        int remaining = message.getGameInfo().getNumOfTotalPlayers() - message.getGameInfo().getNumOfWaitingPlayers();
        String s = String.format("A new player joined. %d more players needed...", remaining);
        System.out.println(s);
        cli.getView().setExpertMode(message.getGameInfo().isExpertGame());

        if (remaining == 0){
            cli.setSuccess(true);
        }
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(AvailableTowers message) {
        cli.getView().setAvailableTowers(message.getAvailableTowers());
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }
}
