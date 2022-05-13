package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.fromServer.JoinAlreadyExistingGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    private final int socketPort;
    private final ServerSocket socket;
    private final ArrayList<ServerClientConnection> lobby;
    private ServerClientConnection host;
    private final ArrayList<String> takenNicknames;
    private final ArrayList<GameHandler> activeGames;
    private final int SOCKET_TIMEOUT = 2000000;

    private int gameID;
    private int numberOfPlayers;
    private boolean expertMode;

    public Server(int socketPort) throws IOException {
        this.socketPort = socketPort;
        socket = new ServerSocket(socketPort);
        lobby = new ArrayList<>();
        takenNicknames = new ArrayList<>();
        activeGames = new ArrayList<>();
        gameID = 1;
        numberOfPlayers = 0;
        expertMode = false;
    }

    public int getSocketPort() {
        return socketPort;
    }

    @Override
    public void run() {
        System.out.println("Server running on address " + socket.getInetAddress());
        System.out.println("and on port " + socket.getLocalPort());

        while(true) {
            try {
                Socket client = socket.accept();
                client.setSoTimeout(SOCKET_TIMEOUT);
                ServerClientConnection socketConnection = new ServerClientConnection(this, client);
                new Thread(socketConnection).start();
            } catch (IOException e) {
                System.out.println("Connection dropped.");
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }

    /**
     * Checks if newNickname has already been used or not
     * @param newNickname nickname checked
     * @return true if the nickname is valid, false if it has to be changed
     */
    public boolean checkNickname(String newNickname) {
        return takenNicknames.stream().noneMatch(client -> client.equalsIgnoreCase(newNickname));
    }

    public void registerUser(ServerClientConnection client) {
        if (! lobby.contains(client)) {
            lobby.add(client);
            takenNicknames.add(client.getNickname());
            updateLobby();
        }
    }

    public void updateLobby() {
        if (lobby.size() == 1) {
            host = lobby.get(0);
            host.askNewGame();
        } else {
            for (ServerClientConnection client : lobby) {
                client.sendMessage(
                        new JoinAlreadyExistingGame(gameID, lobby.size(), numberOfPlayers, expertMode));
            }
        }
        if (lobby.size() >= numberOfPlayers && numberOfPlayers != 0) {
            ArrayList<ServerClientConnection> players = new ArrayList<>();
            for (int i = 0; i < numberOfPlayers; i++) {
                players.add(lobby.remove(0));
            }
            GameHandler gameHandler = new GameHandler(gameID, players, expertMode, numberOfPlayers);
            gameID++;
            expertMode = false;
            numberOfPlayers = 0;
            for (ServerClientConnection player : players) {
                player.setGame(gameHandler);
            }
            activeGames.add(gameHandler);
        }
    }

    public GameHandler getGameByNickname(String nickname) {
        for(GameHandler game : activeGames) {
            for (ServerClientConnection player : game.getPlayers()) {
                if (player.getNickname().equals(nickname)) {
                    return game;
                }
            }
        }
        return null;
    }
}
