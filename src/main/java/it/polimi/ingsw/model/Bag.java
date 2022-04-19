package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Bag {

    private final ArrayList<Student> students;  /*students may be final*/

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

    public int getStudentsLeft() {
        return students.size();
    }

    public void addStudent(Student student) {
        students.add(student);
        Collections.shuffle(students);
    }

    public Student drawStudent() {
        Student t = students.get(0);
        students.remove(0);
        return t;
    }
}
