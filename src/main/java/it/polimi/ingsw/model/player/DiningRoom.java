package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.StudentDeposit;

import java.io.Serializable;
import java.util.EnumMap;

public class DiningRoom implements StudentDeposit, Serializable {
    private final EnumMap<Color, Integer> students;
    private final SchoolBoard schoolBoard;
    private boolean enableCoins=false;

    public DiningRoom(SchoolBoard schoolBoard) {
        this.schoolBoard=schoolBoard;
        this.students = new EnumMap<>(Color.class);
        students.put(Color.YELLOW, 0);
        students.put(Color.BLUE, 0);
        students.put(Color.GREEN, 0);
        students.put(Color.RED, 0);
        students.put(Color.PINK, 0);
    }

    /**
     * method used to add a student to the dining room
     * @param color the color of the student to add
     */
    @Override
    public void addStudent(Color color) {
        students.put(color, students.get(color) + 1);
        if (enableCoins && students.get(color)%3==0){
            schoolBoard.addCoin();
        }
    }

    /**
     * method used to extract a student from the dining room
     * @param color the color of the student to extract
     * @throws StudentNotExistingException exception thrown when the selected student does not exist
     */
    @Override
    public void extractStudent(Color color) throws StudentNotExistingException {
        if (students.get(color) == 0) {
            throw new StudentNotExistingException("There is no " + color.name() + " student in this dining room.");
        } else {
            students.put(color, students.get(color) - 1);
        }
    }

    /**
     * method used to get the number of the students of a selected color
     * @param color the color of the requested students
     * @return the number of the requested students
     */
    public int getStudentsNumber(Color color) {
        return students.get(color);
    }

    /**
     * method used to enable the coins in the dining room
     */
    public void setEnableCoins(){
        enableCoins=true;
    }
}
