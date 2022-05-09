package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import java.util.ArrayList;

public class Char1 extends CharacterCard{

    private final ArrayList<Color> students;

    Char1() {
        cost = 1;
        students=new ArrayList<>();
    }


    //In setup draw 4 students and place them on this card
    public void setUp (Bag bag){
        while(students.size()<4) {
            students.add(bag.drawStudent());
        }
    }

    public void addStudent(Color c){this.students.add(c);}

    public ArrayList<Color> getStudents(){return students;}

    //Take 1 student from this card and place it on an Island of your choice
    public void removeStudent(Color c){this.students.remove(c);}
}
