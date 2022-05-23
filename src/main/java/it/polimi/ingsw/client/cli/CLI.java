package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.gameStates.State;
import it.polimi.ingsw.exceptions.*;

import java.io.PrintWriter;
import java.util.Scanner;

public class CLI {
    private State gameState;

    public void setGameState(State gameState){
        this.gameState = gameState;
    }

}