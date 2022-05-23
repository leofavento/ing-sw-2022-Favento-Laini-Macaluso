package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class View {
    private String currentPlayer;
    private Dashboard dashboard;
    private ArrayList<Player> players;
    private HashMap<String, Assistant> playedAssistants;
    private int roundNumber;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setPlayedAssistant(String nickname, Assistant playedAssistant) {
        playedAssistants.put(nickname, playedAssistant);
    }

    public HashMap<String, Assistant> getPlayedAssistants() {
        return playedAssistants;
    }
}
