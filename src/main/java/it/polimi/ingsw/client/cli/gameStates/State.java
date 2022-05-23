package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;

import java.io.IOException;

public interface State {
    void doAction(CLI cli);
}
