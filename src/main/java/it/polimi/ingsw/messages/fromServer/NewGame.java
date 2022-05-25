package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class NewGame implements Message {
    private final ArrayList<Player> players;

    public NewGame(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getMessage() {
        return "The game is starting...";
    }
}
