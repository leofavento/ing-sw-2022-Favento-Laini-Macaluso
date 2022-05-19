package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.StudentDeposit;

import java.util.ArrayList;

public class Entrance implements StudentDeposit {
    private final ArrayList<Color> students;

    public Entrance() {
        students = new ArrayList<>();
    }

    @Override
    public void addStudent(Color color) {
        students.add(color);
    }

    @Override
    public void extractStudent(Color color) throws StudentNotExistingException {
        if (!students.contains(color)){
        throw new StudentNotExistingException("There is no" + color.name() + "student in your entrance");
        }
        else {
            students.remove(color);
        }
    }

    public ArrayList<Color> getStudents() {
        return students;
    }
}
