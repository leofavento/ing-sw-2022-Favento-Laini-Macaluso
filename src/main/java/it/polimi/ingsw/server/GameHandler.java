package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

public class GameHandler {
    private Game game;
    private Controller controller;

    private int gameID;
    private boolean expertMode;
    private int numberOfPlayers;

    private ArrayList<ServerClientConnection> players;

    public GameHandler(int gameID, ArrayList<ServerClientConnection> players, boolean expertMode, int numOfPlayers) {
        this.gameID = gameID;
        this.players = players;
        this.expertMode = expertMode;
        this.numberOfPlayers = numOfPlayers;

        game = new Game(gameID, numberOfPlayers, expertMode);
        controller = new Controller(game);
        controller.getState().execute(game);
    }
}
