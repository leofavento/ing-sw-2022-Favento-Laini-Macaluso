package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class GameHandler implements Observer<Message> {
    private final Game game;
    private final Controller controller;

    private final int gameID;
    private final boolean expertMode;
    private final int numberOfPlayers;

    private final ArrayList<ServerClientConnection> players;

    public GameHandler(int gameID, ArrayList<ServerClientConnection> players, boolean expertMode, int numOfPlayers) {
        this.gameID = gameID;
        this.players = players;
        this.expertMode = expertMode;
        this.numberOfPlayers = numOfPlayers;

        game = new Game(gameID, numberOfPlayers, expertMode);
        controller = new Controller(game);
        controller.addObserver(this);
        controller.getState().execute();
    }

    public ArrayList<ServerClientConnection> getPlayers() {
        return players;
    }

    public Controller getController() {
        return controller;
    }

    public void readMessage(String nickname, Message message) {
        if (! nickname.equals(game.getCurrentPlayer().getNickname())) {
            sendMessageByNickname(nickname, ErrorMessage.WRONG_TURN);
        } else {
            controller.getState().receiveMessage(message, nickname);
        }
    }

    public void sendMessageByNickname(String nickname, Message message) {
        for (ServerClientConnection player : players) {
            if (player.getNickname().equals(nickname)) {
                player.sendMessage(message);
            }
        }
    }

    public void broadcastMessage(Message message) {
        for (ServerClientConnection player : players) {
            sendMessageByNickname(player.getNickname(), message);
        }
    }

    public void sendToAllExcept(String nickname, Message message) {
        for (ServerClientConnection player : players) {
            if (! player.getNickname().equals(nickname)) {
                sendMessageByNickname(player.getNickname(), message);
            }
        }
    }

    @Override
    public void update(Message message) {
        String nickCurrentPlayer = game.getCurrentPlayer().getNickname();

        if (message instanceof AvailableTowers) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof AvailableWizards) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof ErrorMessage) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message == CommunicationMessage.SUCCESS) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof PlayerStatusMessage) {
            sendMessageByNickname(nickCurrentPlayer, message);
            sendToAllExcept(nickCurrentPlayer, new PlayerStatusMessage(PlayerStatus.WAITING));
        } else if (message instanceof PlayedAssistant) {
            sendMessageByNickname(nickCurrentPlayer, CommunicationMessage.SUCCESS);
            sendToAllExcept(nickCurrentPlayer, message);
        }
    }
}
