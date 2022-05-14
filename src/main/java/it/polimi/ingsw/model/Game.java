package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Game {
    private final int ID;
    private final ArrayList<Player> players;
    private final ArrayList<Player> onlinePlayers;
    private Player currentPlayer;
    private final Dashboard dashboard;
    private final int numberOfPlayers;
    private final boolean expertGame;
    private final EnumMap<Tower, ArrayList<Player>> teams;

    public Game(int id, int numberOfPlayers, boolean expertGame){
        ID = id;
        this.players = new ArrayList<>();
        this.onlinePlayers = new ArrayList<>();
        this.dashboard = new Dashboard();
        this.numberOfPlayers = numberOfPlayers;
        this.expertGame = expertGame;
        this.teams = new EnumMap<>(Tower.class);
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void addNewPlayer(Player player) {
        players.add(player);
        onlinePlayers.add(player);
    }

    public Player getPlayer(String nickname){
        for (Player p : onlinePlayers) {
            if (p.getNickname().equals(nickname)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Player> getOnlinePlayers(){
            return onlinePlayers;
        }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setNextPlayer(){
        setCurrentPlayer(onlinePlayers.get((onlinePlayers.indexOf(getCurrentPlayer()) + 1) % onlinePlayers.size()));
    }

    public void initialTowersDeal(){
        switch(numberOfPlayers) {
            case(2):
            case(4):
                teams.get(Tower.BLACK).get(0).getSchoolBoard().setTowersNumber(8);
                teams.get(Tower.WHITE).get(0).getSchoolBoard().setTowersNumber(8);
                break;
            case(3):
                teams.get(Tower.BLACK).get(0).getSchoolBoard().setTowersNumber(6);
                teams.get(Tower.WHITE).get(0).getSchoolBoard().setTowersNumber(6);
                teams.get(Tower.GREY).get(0).getSchoolBoard().setTowersNumber(6);
                break;
        }
    }

    public void dealStudents(Player p, int numStudents){
        for (int i = 0; i < numStudents; i++) {
            p.getSchoolBoard().getEntrance().addStudent(dashboard.getBag().drawStudent());
        }
    }

    public void updateProfessors() {
        for (Color color : Color.values()) {
            // calculate maximum number of students of a certain color in any SchoolBoard
            int max = players.stream()
                    .mapToInt(p -> p.getSchoolBoard().getDiningRoom().getStudentsNumber(color))
                    .max()
                    .orElseThrow(NoSuchElementException::new);

            // List of players having maximum number of students of a certain color
            List<Player> newOwners = players.stream()
                    .filter(p -> p.getSchoolBoard().getDiningRoom().getStudentsNumber(color) == max)
                    .collect(Collectors.toList());

            // if the former owner of the professor is not in the list AND there is only one newOwner, proceed to change the owner
            if (!newOwners.contains(dashboard.getProfessors()[color.ordinal()].getOwner()) && newOwners.size() == 1) {
                // remove Professor from former owner's SchoolBoard
                if (dashboard.getProfessors()[color.ordinal()].getOwner() != null) {
                    dashboard.getProfessors()[color.ordinal()]
                            .getOwner()
                            .getSchoolBoard()
                            .removeProfessor(dashboard.getProfessors()[color.ordinal()]);
                }

                // change owner in Professor object
                dashboard.getProfessors()[color.ordinal()]
                        .changeOwner(newOwners.get(0));

                // add new Professor in new owner's SchoolBoard
                newOwners.get(0)
                        .getSchoolBoard()
                        .addProfessor(dashboard.getProfessors()[color.ordinal()]);
            }
        }
    }

    public ArrayList<Player> getTeamFromTower(Tower tower) {
        return teams.get(tower);
    }

    public void addPlayerToTeam(Tower tower, Player player) {
        teams.putIfAbsent(tower, new ArrayList<>());
        teams.get(tower).add(player);
    }

    public List<Tower> getTeams() {
        return new ArrayList<>(teams.keySet());
    }
}
