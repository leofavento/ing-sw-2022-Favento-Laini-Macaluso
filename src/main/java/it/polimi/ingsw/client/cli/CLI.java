package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.cli.gameStates.State;
import it.polimi.ingsw.model.GameInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CLI implements Runnable {
    private State gameState;
    private Client client;
    private View view;
    private boolean success;
    private ArrayList<GameInfo> availableGames;

    public void setGameState(State gameState) {
        this.gameState = gameState;
        new Thread(gameState).start();
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

    public View getView() {
        return view;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<GameInfo> getAvailableGames() {
        return availableGames;
    }

    public void setAvailableGames(ArrayList<GameInfo> availableGames) {
        this.availableGames = availableGames;
    }

    public boolean isLastPlayer() {
        return Objects.equals(view.getPlayers().get(view.getPlayers().size() - 1).getNickname(),
                client.getNickname());
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
        view = new View();
        try {
            client.startConnection(port, ip);
            client.setMessageReceiver(new MessageReceiver(this, client));
            new Thread(client).start();
        } catch (IOException e) {
            System.out.println("The client can't connect to the specified server.");
        }
    }
}