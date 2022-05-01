package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientConnection implements Runnable {
    private Server server;
    private Socket socket;
    private String nickname;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean active;

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

    }

    public synchronized void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
