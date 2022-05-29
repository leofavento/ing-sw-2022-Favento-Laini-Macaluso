package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.messages.fromServer.AvailableGames;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.Ping;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.Observable;

public class ServerClientConnection implements Observable<Message>, Runnable {
    private final Server server;
    private final Socket socket;
    private String nickname;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean active;
    private GameHandler gameHandler;
    private boolean requestedNickname;
    private boolean isPlaying;

    private final List<Observer<Message>> observers = new ArrayList<>();

    public ServerClientConnection(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
        active = true;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        Runnable pingSender = () -> {
            while (active) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (active) {
                    sendMessage(new Ping());
                }
            }
        };
        new Thread(pingSender).start();

        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            askNickname();
            while (isActive()) {
                Message received = (Message) input.readObject();
                readMessage(received);
                System.out.printf("From %s: %s%n", nickname, received);
            }
        } catch (IOException e) {
            if (gameHandler != null) {
                gameHandler.disconnect(this);
            }
            close();
            System.err.println(nickname + " disconnected.");
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid stream from object");
        }

        setActive(false);
        close();
    }

    public synchronized void sendMessage(Message message) {
        try {
            output.reset();
            output.writeObject(message);
            output.flush();
            System.out.printf("To %s: %s%n", nickname, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void readMessage(Message message) {
        if (message instanceof Disconnect) {
            gameHandler.disconnect(this);
            close();
        } else if (message instanceof LoginMessage && requestedNickname) {
            LoginMessage loginMessage = (LoginMessage) message;
            if (server.checkNickname(loginMessage.getNickname())) {
                requestedNickname = false;
                nickname = loginMessage.getNickname();
                server.registerUser(this);
            } else {
                sendMessage(ErrorMessage.TAKEN_NICKNAME);
            }
        } else if (message instanceof SetGame && !isPlaying) {
            SetGame setGame = (SetGame) message;
            createGame(setGame);
        } else if (message instanceof RequestGames) {
            sendMessage(new AvailableGames(server.getAvailableGames()));
        } else if (message instanceof JoinAvailableGame) {
            JoinAvailableGame joinAvailableGame = (JoinAvailableGame) message;
            joinAvailableGame(joinAvailableGame);
        } else if (message instanceof Pong) {
        } else if (isPlaying) {
            gameHandler.readMessage(nickname, message);
        }
    }

    private void createGame(SetGame setGame) {
        if (setGame.getNumberOfPlayers() >= 2 && setGame.getNumberOfPlayers() <= 4) {
            gameHandler = server.createRoom(this, setGame.getNumberOfPlayers(), setGame.getExpertMode());
        } else {
            sendMessage(ErrorMessage.INVALID_SETTINGS);
        }
    }

    private void joinAvailableGame(JoinAvailableGame joinAvailableGame) {
        int gameID = joinAvailableGame.getGameID();
        if (server.getStartingGameByID(gameID) != null) {
            server.getStartingGameByID(gameID).joinGame(this);
        } else {
            sendMessage(ErrorMessage.INVALID_INPUT);
        }
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public synchronized void close() {
        try {
            gameHandler.getPlayers().remove(this);
        } catch (NullPointerException ignored) {
        }
        server.unregisterUser(this);
        try {
            input.close();
        } catch (IOException ignored) {
        }
        try {
            output.close();
        } catch (IOException ignored) {
        }
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    public void askNickname() {
        sendMessage(CommunicationMessage.ENTER_NICKNAME);
        requestedNickname = true;
    }

    public String getNickname() {
        return nickname;
    }

    public void setGame(GameHandler game) {
        this.gameHandler = game;
    }

    @Override
    public void addObserver(Observer<Message> observer) {
        observers.add(observer);
    }

    @Override
    public void notify(Message message) {
        for (Observer<Message> observer : observers) {
            observer.update(message);
        }
    }
}
