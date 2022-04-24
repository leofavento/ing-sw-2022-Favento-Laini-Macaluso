package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Game {
    private final ArrayList<Player> players;
    private final ArrayList<Player> onlinePlayers;
    private Player currentPlayer;
    private final Dashboard dashboard;

    public Game(){
        this.players=new ArrayList<>();
        this.onlinePlayers=new ArrayList<>();
        dashboard = new Dashboard();
    }

    public Dashboard getDashboard() {
        return dashboard;
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
        //TODO
    }

    public void dealStudents(Player p, int numStudents){
        for (int i = 0; i < numStudents; i++) {
            p.getSchoolBoard().addToEntrance(dashboard.getBag().drawStudent());
        }
    }

    public void updateProfessors(){
        for (Color color : Color.values()) {
            // calculate maximum number of students of a certain color in any SchoolBoard
            int max = players.stream()
                    .mapToInt(p -> p.getSchoolBoard().getColor(color))
                    .max()
                    .orElseThrow(NoSuchElementException::new);

            // List of players having maximum number of students of a certain color
            List<Player> newOwners = players.stream()
                    .filter(p -> p.getSchoolBoard().getColor(color) == max)
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
}
