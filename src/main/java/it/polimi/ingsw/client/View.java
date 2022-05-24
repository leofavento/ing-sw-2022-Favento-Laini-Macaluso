package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class View {
    private String currentPlayer;
    private Dashboard dashboard;
    private ArrayList<Player> players;
    private HashMap<String, Assistant> playedAssistants;
    private int roundNumber;
    private boolean expertMode;
    private HashMap<Tower, Integer> availableTowers;
    private ArrayList<Integer> availableWizards;
    private ArrayList<Assistant> availableAssistants;
    private int totalPlayers;
    private int activePlayers;
    private boolean isHost;
    private ArrayList<Color> movableStudents;


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

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public HashMap<Tower, Integer> getAvailableTowers() {
        return availableTowers;
    }

    public void setAvailableTowers(HashMap<Tower, Integer> availableTowers) {
        this.availableTowers = availableTowers;
    }

    public ArrayList<Assistant> getAvailableAssistants(){
        return availableAssistants;
    }

    public void setAvailableAssistants(ArrayList<Assistant> availableAssistants){
        this.availableAssistants = availableAssistants;
    }

    public ArrayList<Integer> getAvailableWizards() {
        return availableWizards;
    }

    public void setAvailableWizards(ArrayList<Integer> availableWizards) {
        this.availableWizards = availableWizards;
    }

    public int getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(int activePlayers) {
        this.activePlayers = activePlayers;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public ArrayList<Color> getMovableStudents() {
        return movableStudents;
    }

    public void setMovableStudents(ArrayList<Color> movableStudents) {
        this.movableStudents = movableStudents;
    }
}
