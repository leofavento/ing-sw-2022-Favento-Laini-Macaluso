package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;

/**
 * this Character allows the player to choose an Island and resolve it as if Mother Nature ended her movement there
 */
public class Char3 extends CharacterCard {

    public Char3() {
        value = CharacterEnum.valueOf(("Char3"));
        this.cost = 3;
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
