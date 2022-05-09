package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.StudentDeposit;

import java.util.EnumMap;

public class DiningRoom implements StudentDeposit {
    private final EnumMap<Color, Integer> students;

    public DiningRoom() {
        this.students = new EnumMap<>(Color.class);
        students.put(Color.YELLOW, 0);
        students.put(Color.BLUE, 0);
        students.put(Color.GREEN, 0);
        students.put(Color.RED, 0);
        students.put(Color.PINK, 0);
    }

    @Override
    public void addStudent(Color color) {students.put(color, students.get(color) + 1);}

    @Override
    public void extractStudent(Color color) throws StudentNotExistingException {
        if (students.get(color) == 0) {
            throw new StudentNotExistingException("There is no " + color.name() + " student in this dining room.");
        } else {
            students.put(color, students.get(color) - 1);
        }
    }

    public int getStudentsNumber(Color color) {
        return students.get(color);
    }
}
