package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;

public class Char3 extends CharacterCard {

    public Char3() {
        value = CharacterEnum.valueOf(("Char3"));
        this.cost = 3;
    }

    //Choose an island and resolve the island as if Mother Nature had ended her movement here.
    @Override
    public void activate(CharacterController controller) {
        controller.activate(this);
    }
}
