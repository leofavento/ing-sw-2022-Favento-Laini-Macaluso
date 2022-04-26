package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Cloud {

    private final ArrayList<Color> students;

    public Cloud() {
        students = new ArrayList<>();
    }

    public void addStudent(Color s){
        students.add(s);
    }

    public ArrayList<Color> getStudents(){
        ArrayList<Color> temp = new ArrayList<>(students);
        students.clear();
        return temp;
    }
}
