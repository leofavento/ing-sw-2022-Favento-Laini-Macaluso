package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;

/**
 * this Character allows the player to pick a color. Every player must return 3 Students of that color from their Dining Room to the bag. If any player has fewer than 3 Students of that color, return as many Students as he has.
 **/
public class Char12 extends CharacterCard{

    public Char12(){
        value= CharacterEnum.valueOf(("Char12"));
        cost=3;
        description = "Choose a type of Student: every player (including yourself) must return 3 Students " +
                "of that type from their Dining Room to the bag. If any player has fewer than 3 Students " +
                "of that type, return as many Students as they have.";
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
