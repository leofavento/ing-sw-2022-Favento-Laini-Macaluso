package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.DiningRoom;

public class Char12 extends CharacterCard{

    public Char12(){cost=3;}

    //Every player must return 3 Students of that type from their DiningRoom to the bag. If any player has fewer than 3 Students of that color, return as many Students as they have

    /*public void effect(Color c, DiningRoom diningRoom, Bag bag) throws StudentNotExistingException {
        for (int z=0; z<3; z++){
            diningRoom.extractStudent(c);
            bag.addStudent(c);
        }
    }*/

}
