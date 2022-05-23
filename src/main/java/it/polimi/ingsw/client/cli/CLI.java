package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.gameStates.ConnectionState;
import it.polimi.ingsw.client.cli.gameStates.State;

public class CLI implements Runnable {
    private State gameState;
    private Client client;

    public void setGameState(State gameState){
        this.gameState = gameState;
        gameState.doAction(this);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        setGameState(new ConnectionState());
        gameState.doAction(this);
    }
}