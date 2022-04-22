package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<Player> onlinePlayers;
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
            int max = players.stream()
                    .mapToInt(p -> p.getSchoolBoard().getColor(color))
                    .max()
                    .orElseThrow(NoSuchElementException::new);

            List<Player> newOwners = players.stream()
                    .filter(p -> p.getSchoolBoard().getColor(color) == max)
                    .collect(Collectors.toList());

            if (! newOwners.contains(dashboard.getProfessors()[color.ordinal()].getOwner())) {
                dashboard.getProfessors()[color.ordinal()].changeOwner(newOwners.get(0));
            }
        }
    }





}
