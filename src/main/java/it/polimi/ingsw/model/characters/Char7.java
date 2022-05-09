package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

public class Char7 extends CharacterCard{

    public Char7(){
        students = new ArrayList<>();
        cost=1;
    }

    @Override
    public void setUp (Bag bag){
        while(students.size()<7) {
            students.add(bag.drawStudent());
        }
    }

    @Override
    public void addStudent(Color c){this.students.add(c);}

    //Take 3 student from this card and replace them with the same number of Students from entrance
    @Override
    public void removeStudent(Color c){this.students.remove(c);}


}
