package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;

/**
 * this Character allows the player to exchange up to 2 students between his Entrance and his Dining Room
 */
public class Char10 extends CharacterCard {

    public Char10() {
        value = CharacterEnum.valueOf(("Char10"));
        this.cost = 1;
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
