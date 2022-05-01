package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

public class Professor {
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
