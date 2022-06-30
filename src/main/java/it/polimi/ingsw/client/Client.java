package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.CLIMessageReceiver;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.FromServerMessage;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String nickname;
    private boolean active;
    private MessageReceiver messageReceiver;
    private final GUI gui;

    public Client(GUI gui) {
        this.gui = gui;
    }


    public void startConnection(int port, String IP_address) throws IOException {
        clientSocket = new Socket(IP_address, port);
        System.out.println("Client: started");
        System.out.println("Client socket: " + clientSocket);
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());

        active = true;
    }

    public void setMessageReceiver(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public synchronized void sendMessage(Message message) {
        try {
            synchronized (output) {
                output.writeObject(message);
                output.reset();
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            active = false;
        }
    }


    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (NullPointerException | IOException ignored) {
        }
        try {
            input.close();
        } catch (NullPointerException | IOException ignored) {
        }
        try {
            output.close();
        } catch (NullPointerException | IOException ignored) {
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run() {
        while (active) {
            try {
                FromServerMessage received = (FromServerMessage) input.readObject();
                received.receiveMessage(messageReceiver);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Connection closed from server.");
                active = false;
                if (gui != null) {
                    gui.close();
                    Platform.runLater(() -> {
                        gui.initializeStage(true);
                        gui.execute();
                    });
                } else {
                    System.exit(0);
                }
                closeConnection();
            }
        }
    }
}
