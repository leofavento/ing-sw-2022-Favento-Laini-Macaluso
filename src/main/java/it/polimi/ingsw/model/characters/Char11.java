package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.DiningRoom;

import java.util.ArrayList;

public class Char11 extends CharacterCard{

    public Char11(){
        value= CharacterEnum.valueOf(("Char11"));
        cost=2;
        students=new ArrayList<>();
    }

    //In setup draw 4 students and place them on this card
    @Override
    public void setUp (Bag bag){
        while(students.size()<4) {
            students.add(bag.drawStudent());
        }
    }

    @Override
    public void addStudent(Color c){
        this.students.add(c);}

    //Take 1 student from this card and place it on your DiningRoom
    /*public void effect(Color c, DiningRoom diningRoom){
        this.students.remove(c);
        diningRoom.addStudent(c);
    }*/


}
