package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.EndOfGameReason;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class View {
    private String currentPlayer;
    private Dashboard dashboard;
    private ArrayList<Player> players;
    private HashMap<String, Assistant> playedAssistants = new HashMap<>();
    private int roundNumber;
    private boolean expertMode;
    private HashMap<Tower, Integer> availableTowers;
    private ArrayList<Integer> availableWizards;
    private ArrayList<Assistant> availableAssistants;
    private int totalPlayers;
    private int activePlayers;
    private boolean isHost;
    private ArrayList<Color> movableStudents;
    private ArrayList<Color> movableStudentsChar;
    private final ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.values()));
    private int motherNatureSteps;
    private Tower winnerTeam;
    private ArrayList<String> winners;
    private EndOfGameReason endOfGameReason;
    private ErrorMessage lastErrorMessage;
    private boolean requiredDestination;
    private PlayerStatus currentStatus;
    private boolean activatedCharacterEffect=false;

    public void resetView() {
        currentPlayer = null;
        dashboard = null;
        players = null;
        playedAssistants = new HashMap<>();
        roundNumber = 0;
        totalPlayers = 0;
        activePlayers = 0;
        isHost = false;
        movableStudents = null;
        movableStudentsChar = null;
        winnerTeam = null;
        winners = null;
        endOfGameReason = null;
        lastErrorMessage = null;
        requiredDestination = false;
        currentStatus = null;
        activatedCharacterEffect = false;
    }

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

    public Tower getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Tower team) {
        winnerTeam = team;
    }

    public void setWinners(ArrayList<String> winners) {
        this.winners = winners;
    }

    public ArrayList<String> getWinners() {
        return winners;
    }

    public EndOfGameReason getEndOfGameReason() {
        return endOfGameReason;
    }

    public void setEndOfGameReason(EndOfGameReason endOfGameReason) {
        this.endOfGameReason = endOfGameReason;
    }

    public void setLastErrorMessage(ErrorMessage errorMessage) {
        lastErrorMessage = errorMessage;
    }

    public ErrorMessage getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void setRequiredDestination(boolean requiredDestination) {
        this.requiredDestination = requiredDestination;
    }

    public boolean getRequiredDestination() {
        return requiredDestination;
    }

    public ArrayList<Color> getMovableStudentsChar() {
        return movableStudentsChar;
    }

    public void setMovableStudentsChar(ArrayList<Color> movableStudentsChar) {this.movableStudentsChar = movableStudentsChar;}

    public ArrayList<Color> getColors(){return colors;}

    public void setCurrentStatus(PlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public PlayerStatus getCurrentStatus() {
        return currentStatus;
    }

    public boolean getActivatedCharacterEffect(){
        return activatedCharacterEffect;}

    public void setActivatedCharacterEffect(Boolean b){
        activatedCharacterEffect=b;
    }
}
