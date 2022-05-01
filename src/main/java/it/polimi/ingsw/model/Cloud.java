package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.StudentNotExistingException;

import java.util.ArrayList;

public class Cloud implements StudentDeposit {

    private final ArrayList<Color> students;

    public Cloud() {
        students = new ArrayList<>();
    }

    @Override
    public void addStudent(Color student){
        students.add(student);
    }

    @Override
    public void extractStudent(Color color) throws StudentNotExistingException {
        if (! students.contains(color)) {
            throw new StudentNotExistingException("There is no " + color.name() + " student in this cloud.");
        } else {
            students.remove(color);
        }
    }

    public ArrayList<Color> getStudents(){
        ArrayList<Color> temp = new ArrayList<>(students);
        students.clear();
        return temp;
    }
}
