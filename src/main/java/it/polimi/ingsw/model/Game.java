package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Game {
    private final ArrayList<Player> players;
    private final ArrayList<Player> onlinePlayers;
    private Player currentPlayer;
    private Dashboard dashboard;

    public Game(){
        this.players=new ArrayList<>();
        this.onlinePlayers=new ArrayList<>();
    }


    //public Player getPlayer(String nickname){
        //TODO
        //don't know what this should do
    //}

    public ArrayList<Player> getOnlinePlayers(){
            return onlinePlayers;
        }

    public Player getCurrentPlayer(){
            return currentPlayer;
        }

    public void initialTowersDeal(){
        //TODO
    }

    public void setNextPlayer(){
        //TODO
    }

    public void dealStudents(Player p, int numStudents){
        //TODO
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

            // if the former owner of the professor is not in the list, proceed to change the owner
            if (! newOwners.contains(dashboard.getProfessors()[color.ordinal()].getOwner())) {
                // remove Professor from former owner's SchoolBoard
                dashboard.getProfessors()[color.ordinal()]
                        .getOwner()
                        .getSchoolBoard()
                        .removeProfessor(dashboard.getProfessors()[color.ordinal()]);

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
