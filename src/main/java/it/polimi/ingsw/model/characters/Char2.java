package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;

/**
 * this Character allows the player to take control (only during this turn) of any number of Professors, even if he has the same number of Students as the player who currently controls them
 */
public class Char2 extends CharacterCard {

    public Char2() {
        value = CharacterEnum.valueOf(("Char2"));
        this.cost = 2;
        description = "During this turn, you take control of any number of Professors even if you have " +
                "the same number of Students as the player who currently controls them.";
    }

    @Override
    public void activate(CharacterController controller) {
        controller.activate(this);
    }
}
