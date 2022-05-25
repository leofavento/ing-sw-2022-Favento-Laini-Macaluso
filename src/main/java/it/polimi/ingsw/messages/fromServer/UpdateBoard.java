package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class UpdateBoard implements Message {
    private final Dashboard dashboard;
    private final ArrayList<Player> players;

    public UpdateBoard(Dashboard dashboard, ArrayList<Player> players) {
        this.dashboard = dashboard;
        this.players = players;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
