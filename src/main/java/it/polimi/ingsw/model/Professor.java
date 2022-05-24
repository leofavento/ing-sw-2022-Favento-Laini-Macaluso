package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

public class Professor implements Serializable {
    private final Color color;
    private Player owner;

    public Professor(Color color){
        this.color= color;
    }

    public Color getColor(){
        return color;
    }

    public void changeOwner(Player player){
        owner = player;
    }

    public Player getOwner(){
        return owner;
    }
}
