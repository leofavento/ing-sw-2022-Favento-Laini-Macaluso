package it.polimi.ingsw.model.characters;

public class Char2 extends CharacterCard {

    public Char2(){
        value= CharacterEnum.valueOf(("Char2"));
        this.cost=2;
    }

    //During this turn you take control of any number of Professors even if you have the same number of Students as the player who currently controls them.

}
