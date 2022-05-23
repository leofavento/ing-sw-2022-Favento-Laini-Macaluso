package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;

import java.io.IOException;

public abstract class State {
    public abstract void doAction(CLI cli) throws IOException;
}
