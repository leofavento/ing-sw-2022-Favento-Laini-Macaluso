package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Entrance;

public class Char10 extends CharacterCard{

    public Char10(){this.cost=1;}

    //You may exchange up to 2 students between your Entrance and your dining room

    /*public void effect(Color c1, Color c2, Entrance entrance, DiningRoom diningRoom) throws StudentNotExistingException {

        entrance.extractStudent(c1);
        diningRoom.addStudent(c1);

        diningRoom.extractStudent(c2);
        entrance.addStudent(c2);
    }*/

}
