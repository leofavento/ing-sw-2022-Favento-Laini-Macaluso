package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;

public class Char4 extends CharacterCard {

    public Char4() {
        value = CharacterEnum.valueOf(("Char4"));
        this.cost = 1;
    }

    //You may move mother nature up to 2 additional islands
    @Override
    public void activate(CharacterController controller) {
        controller.activate(this);
    }
}
