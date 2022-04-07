package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Cloud {

    private ArrayList<Student> students;

    public Cloud() {students = new ArrayList<>();}

    public void AddStudent(Student s){
        students.add(s);
    }

    public ArrayList<Student> get_Students(){
        return students;
    }
}
