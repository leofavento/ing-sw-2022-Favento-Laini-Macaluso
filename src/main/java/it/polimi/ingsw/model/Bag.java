package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Bag {

    private ArrayList<Student> students;  /*students may be final*/

    public Bag(){
        students = new ArrayList<>();
    }

    public void refill(int studentsNum){
        for (int i=0; i < studentsNum; i++) {
            for (Color color : Color.values()) {
                students.add(new Student(color));
            }
        }
        Collections.shuffle(students);
    }
}
