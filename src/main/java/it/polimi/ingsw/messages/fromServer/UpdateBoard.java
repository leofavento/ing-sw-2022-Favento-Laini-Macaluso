package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * Message sent by the server to communicate the latest changes to the game board.
 */
public class UpdateBoard implements FromServerMessage {
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

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
