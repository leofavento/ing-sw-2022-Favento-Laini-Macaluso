package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;

/**
 * this Character allows the player to select a color. During the influence calculation this turn, that color adds no Influence
 */
public class Char9 extends CharacterCard {

    public Char9() {
        value = CharacterEnum.valueOf(("Char9"));
        cost = 3;
    }


    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
