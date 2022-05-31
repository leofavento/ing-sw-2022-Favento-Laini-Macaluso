package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;

/**
 * this Character allows the player to choose an Island and resolve it as if Mother Nature ended her movement there
 */
public class Char3 extends CharacterCard {

    public Char3() {
        value = CharacterEnum.valueOf(("Char3"));
        this.cost = 3;
        description = "Choose an Island and resolve the Island as if Mother Nature had ended her movement there. " +
                "Mother Nature will still move and the Island where she ends her movement will also be resolved.";
    }

    @Override
    public void activate(CharacterController controller) {
        controller.activate(this);
    }
}
