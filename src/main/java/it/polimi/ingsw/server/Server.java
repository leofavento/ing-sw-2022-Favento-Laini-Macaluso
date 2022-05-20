package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.model.GameInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    private final int socketPort;
    private final ServerSocket socket;
    private final ArrayList<String> takenNicknames;
    private final ArrayList<GameHandler> activeGames;
    private final ArrayList<GameHandler> startingGames;
    private final int SOCKET_TIMEOUT = 2000000;

    private int nextGameID;

    public Server(int socketPort) throws IOException {
        this.socketPort = socketPort;
        socket = new ServerSocket(socketPort);
        takenNicknames = new ArrayList<>();
        activeGames = new ArrayList<>();
        startingGames = new ArrayList<>();
        nextGameID = 1;
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
        takenNicknames.add(client.getNickname());
        client.sendMessage(CommunicationMessage.SUCCESS);
    }

    public void unregisterUser(ServerClientConnection client) {
        takenNicknames.remove(client.getNickname());
    }

    public ArrayList<GameInfo> getAvailableGames() {
        ArrayList<GameInfo> availableGames = new ArrayList<>();
        for (GameHandler game : startingGames) {
            availableGames.add(new GameInfo(game.getGameID(),
                    game.getPlayers().size(),
                    game.getNumberOfPlayers(),
                    game.isExpertMode()));
        }
        return availableGames;
    }

    public GameHandler createRoom(ServerClientConnection host, int numberOfPlayers, boolean expertMode) {
        GameHandler room = new GameHandler(this, nextGameID, host, expertMode, numberOfPlayers);
        nextGameID++;
        startingGames.add(room);
        return room;
    }

    public GameHandler getStartingGameByID(int gameID) {
        return startingGames.stream().filter(game -> game.getGameID() == gameID).findAny().orElse(null);
    }

    public void startGame(GameHandler game) {
        startingGames.remove(game);
        activeGames.add(game);
    }
}
