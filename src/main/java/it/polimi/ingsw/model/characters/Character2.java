package it.polimi.ingsw.model.characters;
import it.polimi.ingsw.model.Character;

//Characters that don't need extra attributes

public class Character2 extends Character {

    private Type3 name;

    public Character2(Type3 t){
        this.name=t;
        setInitialCost(t.getCost());
    }

}
