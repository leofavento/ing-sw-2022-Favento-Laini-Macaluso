package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;

public class WaitingPlayersState implements State {
    private final CLI cli;

    public WaitingPlayersState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        // TODO
        System.out.println("Ok");
    }
}
