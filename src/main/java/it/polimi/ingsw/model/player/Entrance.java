package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.StudentDeposit;

import java.io.Serializable;
import java.util.ArrayList;

public class Entrance implements StudentDeposit, Serializable {
    private final ArrayList<Color> students;

    public Entrance() {
        students = new ArrayList<>();
    }

    /**
     * method used to add a student to the entrance
     * @param color the color of the student to add to the entrance
     */
    @Override
    public void addStudent(Color color) {
        students.add(color);
    }

    /**
     * method used to extract a student from the entrance
     * @param color the color of the student to extract
     * @throws StudentNotExistingException exception thrown when the selected student does not exist
     */
    @Override
    public void extractStudent(Color color) throws StudentNotExistingException {
        if (!students.contains(color)){
        throw new StudentNotExistingException("There is no" + color.name() + "student in your entrance");
        }
        else {
            students.remove(color);
        }
    }

    /**
     * method used to get the students from the entrance
     * @return an arraylist containing the students present in the entrance
     */
    public ArrayList<Color> getStudents() {
        return students;
    }
}
