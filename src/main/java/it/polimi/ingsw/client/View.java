package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View {
    private String currentPlayer;
    private Dashboard dashboard;
    private ArrayList<Player> players;
    private final HashMap<String, Assistant> playedAssistants = new HashMap<>();
    private int roundNumber;
    private boolean expertMode;
    private HashMap<Tower, Integer> availableTowers;
    private ArrayList<Integer> availableWizards;
    private ArrayList<Assistant> availableAssistants;
    private int totalPlayers;
    private int activePlayers;
    private boolean isHost;
    private ArrayList<Color> movableStudents;
    private int motherNatureSteps;
    private ArrayList<Cloud> availableClouds;


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

    public void setPlayedAssistants(Map<Player, Assistant> playedAssistants) {
        this.playedAssistants.clear();
        for (Player player : playedAssistants.keySet()) {
            this.playedAssistants.put(player.getNickname(), playedAssistants.get(player));
        }
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

    public int getMotherNatureSteps(){
        return motherNatureSteps;
    }

    public void setMotherNatureSteps(int motherNatureSteps){
        this.motherNatureSteps = motherNatureSteps;
    }

    public ArrayList<Cloud> getAvailableClouds() {
        return availableClouds;
    }

    public void setAvailableClouds(ArrayList<Cloud> availableClouds) {
        this.availableClouds = availableClouds;
    }
}
