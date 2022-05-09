package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Color;

public class Char9 extends CharacterCard{

    public Char9(){
        value= CharacterEnum.valueOf(("Char9"));
        cost=3;
    }

    @Override
    public void setColor(Color c){this.color=c;}

    @Override
    public void resetColor(){this.color=null;}

    //Choose a color of Student: during the influence calculation this turn, that color adds no influence
}
