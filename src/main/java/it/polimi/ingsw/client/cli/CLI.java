package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.gameStates.State;

import java.io.IOException;
import java.util.Scanner;

public class CLI implements Runnable {
    private State gameState;
    private Client client;
    private boolean success;

    public void setGameState(State gameState){
        this.gameState = gameState;
        gameState.doAction(this);
    }

    public State getGameState() {
        return gameState;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);

        System.out.println("Insert the server IP address you want to connect to: ");
        in.reset();
        String ip = in.next();

        System.out.println("Insert the server port: ");
        in.reset();
        int port = in.nextInt();

        client = new Client(true);
        try {
            client.startConnection(port, ip);
            client.setMessageReceiver(new MessageReceiver(this, client));
            new Thread(client).start();
        } catch(IOException e) {
            System.out.println("The client can't connect to the specified server.");
        }
    }
}