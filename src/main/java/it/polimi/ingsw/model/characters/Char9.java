package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Color;

public class Char9 extends CharacterCard{

    private Color color;

    Char9(){
        cost=3;
    }

    public Color getColor(){return color;}

    public void setColor(Color c){this.color=c;}

    public void resetColor(){this.color=null;}


    //Choose a color of Student: during the influence calculation this turn, that color adds no influence
}
