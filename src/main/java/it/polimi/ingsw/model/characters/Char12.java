package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.DiningRoom;

/**
 * this Character allows the player to pick a color. Every player must return 3 Students of that color from their Dining Room to the bag. If any player has fewer than 3 Students of that color, return as many Students as he has.
 **/
public class Char12 extends CharacterCard{

    public Char12(){
        value= CharacterEnum.valueOf(("Char12"));
        cost=3;}

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
