package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;

public class Char6 extends CharacterCard {

    public Char6() {
        value = CharacterEnum.valueOf(("Char6"));
        this.cost = 3;
    }

    //When resolving a Conquering on an Island, Towers do not count towards influence
    @Override
    public void activate(CharacterController controller) {
        controller.activate(this);
    }
}
