package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.AlreadyPlayedCharacterException;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.UseCharacterEffect;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
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
        host.sendMessage(new WaitingForPlayers(gameID));
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

    /**
     * method used to disconnect a player from the game
     *
     * @param player a player to disconnect
     */
    public void disconnect(ServerClientConnection player) {
        players.remove(player);
        if (hasStarted) {
            sendToAllExcept(player.getNickname(), new PlayerDisconnected(player.getNickname()));
            endGame();
        } else {
            if (isHost(player)) {
                endGame(player);
            } else {
                sendToAllExcept(player.getNickname(), new UpdateLobby(gameID, players.size(), numberOfPlayers, expertMode));
            }
        }
    }

    /**
     * method used to end the game for a disconnected player
     *
     * @param disconnectedPlayer a disconnected player
     */
    private void endGame(ServerClientConnection disconnectedPlayer) {
        players.remove(disconnectedPlayer);
        server.getStartingGames().remove(this);
        broadcastMessage(CommunicationMessage.HOST_LEFT);
        while (!players.isEmpty()) {
            players.get(0).close();
            players.remove(players.get(0));
        }
    }

    /**
     * method used to end the current game
     */
    private void endGame() {
        server.getActiveGames().remove(this);
        while (!players.isEmpty()) {
            players.get(0).setGame(null);
            players.get(0).setActive(false);
            players.get(0).setPlaying(false);
            players.get(0).close();
            players.remove(players.get(0));
        }
    }

    /**
     * method used to create a game
     */
    private void createGame() {
        hasStarted = true;
        game = new Game(gameID, numberOfPlayers, expertMode);
        for (ServerClientConnection player : players) {
            game.addNewPlayer(new Player(player.getNickname()));
            player.setPlaying(true);
        }
        broadcastMessage(new MatchStarted(game.getOnlinePlayers()));
        controller = new Controller(game);
        controller.addObserver(this);
        controller.getState().execute();
    }

    /**
     * method used to add a player to an existing game
     *
     * @param player the player to add
     */
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

    /**
     * method used to read a message from a client
     *
     * @param nickname the nickname of the player who is sending the message
     * @param message  object of the message sent
     */
    public synchronized void readMessage(String nickname, Message message) {
        if (!nickname.equals(game.getCurrentPlayer().getNickname()) && !(message instanceof Ack)) {
            sendMessageByNickname(nickname, ErrorMessage.WRONG_TURN);
        } else if (message instanceof UseCharacterEffect) {
            try {
                controller.getCharacterController().applyEffect(((UseCharacterEffect) message).getCharacter());
            } catch (NotEnoughCoinsException e) {
                sendMessageByNickname(nickname, ErrorMessage.NOT_ENOUGH_COINS);
            } catch (InvalidInputException e) {
                sendMessageByNickname(nickname, ErrorMessage.INVALID_INPUT);
            } catch (AlreadyPlayedCharacterException e) {
                sendMessageByNickname(nickname, ErrorMessage.ALREADY_PLAYED_CHARACTER);
            }
        } else {
            controller.getState().receiveMessage(message, nickname);
        }
    }

    /**
     * method used to send a message to a specific player
     *
     * @param nickname the nickname of the desired player
     * @param message  object of the message to be sent
     */
    public void sendMessageByNickname(String nickname, Message message) {
        for (ServerClientConnection player : players) {
            if (player.getNickname().equals(nickname)) {
                player.sendMessage(message);
            }
        }
    }

    /**
     * method used to send a message to every player
     *
     * @param message object of the message to be sent
     */
    public void broadcastMessage(Message message) {
        for (ServerClientConnection player : players) {
            sendMessageByNickname(player.getNickname(), message);
        }
    }

    /**
     * method used to send a message to every player except one
     *
     * @param nickname the nickname of the only player to which the message won't be sent
     * @param message  object of the message to be sent
     */
    public void sendToAllExcept(String nickname, Message message) {
        for (ServerClientConnection player : players) {
            if (!player.getNickname().equals(nickname)) {
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
        } else if (message == CommunicationMessage.LAST_ROUND) {
            broadcastMessage(message);
        } else if (message == CommunicationMessage.NO_ENTRY_TILE_ON_ISLAND) {
            broadcastMessage(message);
        } else if (message == CommunicationMessage.STUDENT_MOVED) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof PlayerStatusMessage) {
            sendMessageByNickname(nickCurrentPlayer, message);
            sendToAllExcept(nickCurrentPlayer, new PlayerStatusMessage(PlayerStatus.WAITING));
        } else if (message instanceof AvailableAssistants) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof PlayedAssistant) {
            broadcastMessage(message);
        } else if (message instanceof UpdateBoard) {
            broadcastMessage(message);
        } else if (message instanceof StartOfPlayerRound) {
            broadcastMessage(message);
        } else if (message instanceof SelectCloud) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof MovableStudents) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof MovableStudentsChar) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof WhereToMove) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof MotherNatureSteps) {
            sendMessageByNickname(nickCurrentPlayer, message);
        } else if (message instanceof EndOfPlayerRound) {
            broadcastMessage(message);
        } else if (message instanceof EndOfRound) {
            broadcastMessage(message);
        } else if (message instanceof IslandOwner) {
            broadcastMessage(message);
        } else if (message instanceof CommunicateWinner) {
            server.getActiveGames().remove(this);
            for (ServerClientConnection player : players) {
                player.setGame(null);
                player.setPlaying(false);
            }
            broadcastMessage(message);
        } else if (message instanceof PlayedCharacter) {
            broadcastMessage(message);
        }
    }
}
