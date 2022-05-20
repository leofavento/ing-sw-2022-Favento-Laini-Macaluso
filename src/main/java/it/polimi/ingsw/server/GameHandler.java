package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class GameHandler implements Observer<Message> {
    private final Server server;
    private final ServerClientConnection host;
    private final int gameID;
    private final int numberOfPlayers;
    private final boolean expertMode;
    private final ArrayList<ServerClientConnection> players;
    private boolean hasStarted;

    private Game game;
    private Controller controller;

    public GameHandler(Server server, int gameID, ServerClientConnection host, boolean expertMode, int numberOfPlayers) {
        this.server = server;
        this.gameID = gameID;
        this.host = host;
        this.numberOfPlayers = numberOfPlayers;
        this.expertMode = expertMode;
        hasStarted = false;
        players = new ArrayList<>();
        players.add(host);
        host.sendMessage(new WaitingForPlayers());
    }

    public int getGameID() {
        return gameID;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public boolean isHost(ServerClientConnection player) {
        return host == player;
    }

    public void disconnect(ServerClientConnection player) {
        if (hasStarted) {
            broadcastMessage(new PlayerDisconnected(player.getNickname()));
            endGame();
        } else {
            if (isHost(player)) {
                endGame(player);
            } else {
                players.remove(player);
                sendToAllExcept(player.getNickname(), new UpdateLobby(gameID, players.size(), numberOfPlayers, expertMode));
            }
        }
    }

    private void endGame(ServerClientConnection disconnectedPlayer) {
        broadcastMessage(CommunicationMessage.HOST_LEFT);
        while (! players.isEmpty()) {
            players.get(0).close();
        }
    }

    private void endGame() {
        while (! players.isEmpty()) {
            players.get(0).close();
        }
    }

    private void createGame() {
        hasStarted = true;
        game = new Game(gameID, numberOfPlayers, expertMode);
        controller = new Controller(game);
        controller.addObserver(this);
        controller.getState().execute();
    }

    public synchronized void joinGame(ServerClientConnection player) {
        if (!isFull()) {
            players.add(player);
            player.setGame(this);
            player.sendMessage(new JoinAlreadyExistingGame(gameID, players.size(), numberOfPlayers, expertMode));
            sendToAllExcept(player.getNickname(), new UpdateLobby(gameID, players.size(), numberOfPlayers, expertMode));
            updateRoom();
        } else {
            player.sendMessage(ErrorMessage.FULL_GAME);
        }
    }

    private void updateRoom() {
        if (isFull()) {
            broadcastMessage(CommunicationMessage.NEW_GAME);
            server.startGame(this);
            createGame();
        }
    }

    private boolean isFull() {
        return players.size() == numberOfPlayers;
    }

    public ArrayList<ServerClientConnection> getPlayers() {
        return players;
    }

    public Controller getController() {
        return controller;
    }

    public void readMessage(String nickname, Message message) {
        if (! nickname.equals(game.getCurrentPlayer().getNickname()) && ! (message instanceof Ack)) {
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
        } else if (message instanceof CommunicateWinner) {
            broadcastMessage(message);
            endGame();
        }
    }
}
