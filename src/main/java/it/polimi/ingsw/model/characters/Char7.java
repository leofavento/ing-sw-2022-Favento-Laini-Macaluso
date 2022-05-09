package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

public class Char7 extends CharacterCard{

    private final ArrayList<Color> students;

    Char7(){
        students = new ArrayList<>();
        cost=1;
    }

    public void setUp (Bag bag){
        while(students.size()<7) {
            students.add(bag.drawStudent());
        }
    }

    public void addStudent(Color c){this.students.add(c);}

    public ArrayList<Color> getStudents(){return students;}

    //Take 3 student from this card and replace them with the same number of Students from entrance
    public void removeStudent(Color c){this.students.remove(c);}
}
