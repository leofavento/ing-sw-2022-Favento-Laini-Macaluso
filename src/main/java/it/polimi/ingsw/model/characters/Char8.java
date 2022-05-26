package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;

/**
 * this Character allows the player to count as having 2 more influence during this turn
 */
public class Char8 extends CharacterCard {

    public Char8() {
        value = CharacterEnum.valueOf(("Char8"));
        cost = 2;
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
