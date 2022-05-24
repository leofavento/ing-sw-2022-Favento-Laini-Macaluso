package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;

public class GameSetupState implements State {
    private final CLI cli;

    public GameSetupState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        System.out.println("^ true");
    }
}
