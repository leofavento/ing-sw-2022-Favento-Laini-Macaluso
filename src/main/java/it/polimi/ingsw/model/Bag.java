package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Bag implements Serializable {

    private final ArrayList<Color> students;

    public Bag(){
        students = new ArrayList<>();
    }

    public void refill(int studentsNum){
        for (int i=0; i < studentsNum; i++) {
            students.addAll(Arrays.asList(Color.values()));
        }
        Collections.shuffle(students);
    }

    public int getStudentsLeft() {
        return students.size();
    }

    public void addStudent(Color student) {
        students.add(student);
        Collections.shuffle(students);
    }

    public Color drawStudent() {
        Color t = students.get(0);
        students.remove(0);
        return t;
    }
}
