package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;

/**
 * this Character allows the player to count as having 2 more influence during this turn
 */
public class Char8 extends CharacterCard {

    public Char8() {
        value = CharacterEnum.valueOf(("Char8"));
        cost = 2;
        description = "During the influence calculation this turn, you count as having 2 more influence.";
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
