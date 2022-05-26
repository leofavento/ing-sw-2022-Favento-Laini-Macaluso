package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;

/**
 * this Character allows the player to move Mother Nature up to 2 additional steps
 */
public class Char4 extends CharacterCard {

    public Char4() {
        value = CharacterEnum.valueOf(("Char4"));
        this.cost = 1;
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
