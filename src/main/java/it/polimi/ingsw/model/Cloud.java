package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Cloud {

    private final ArrayList<Student> students;

    public Cloud() {students = new ArrayList<>();}

    public void AddStudent(Student s){
        students.add(s);
    }

    public ArrayList<Student> getStudents(){
        ArrayList<Student> temp = new ArrayList<>(students);
        students.clear();
        return temp;
    }
}
