package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.messages.fromClient.LoginMessage;
import it.polimi.ingsw.messages.fromClient.SetGame;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
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
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            askNickname();
            while(isActive()) {
                Message received = (Message) input.readObject();
                readMessage(received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid stream from object");
        }

        setActive(false);
        close();
    }

    public void sendMessage(Message message) {
        try {
            output.reset();
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage(Message message) {
        if (message instanceof LoginMessage || message instanceof SetGame) {
            readSetupMessage(message);
        } else {
            gameHandler.readMessage(nickname, message);
        }
    }

    public void readSetupMessage(Message message) {
        if (message instanceof LoginMessage) {
            LoginMessage loginMessage = (LoginMessage) message;
            if (server.checkNickname(loginMessage.getNickname())) {
                nickname = loginMessage.getNickname();
                server.registerUser(this);
            } else {
                sendMessage(ErrorMessage.TAKEN_NICKNAME);
            }
        } else if (message instanceof SetGame) {
            SetGame setGame = (SetGame) message;
        }
    }

    public synchronized void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askNickname() {
        sendMessage(CommunicationMessage.ENTER_NICKNAME);
        // TODO start timer
    }

    public void askNewGame() {
        sendMessage(CommunicationMessage.NEW_GAME);
        // TODO start timer
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
