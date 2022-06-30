package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.FullDiningRoomException;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.StudentDeposit;
import it.polimi.ingsw.model.player.Entrance;
import it.polimi.ingsw.model.player.SchoolBoard;

import java.util.stream.IntStream;

/**
 * Class used to manage the Action phase of every turn
 */
public class Action {

    /**
     * method used to move students from bag to deposit
     * @param bag the game bag
     * @param deposit the game deposit
     * @param number the number of students to move
     */
    public static void moveFromBagToDeposit(Bag bag, StudentDeposit deposit, int number) {
        IntStream.range(0, number)
                .forEach(i -> deposit.addStudent(bag.drawStudent()));
    }

    /**
     * method used to move students from entrance to dining room
     * @param student the color of the student to move
     * @param schoolBoard the game schoolboard
     * @throws FullDiningRoomException exception thrown when the dining room is full
     * @throws StudentNotExistingException exception thrown when the chosen student is not in the entrance
     */
    public static void moveFromEntranceToDining(Color student, SchoolBoard schoolBoard) throws FullDiningRoomException, StudentNotExistingException {
        if (schoolBoard.getDiningRoom().getStudentsNumber(student) == 10) {
            throw new FullDiningRoomException("The dining room is full for " + student.name() + " students.");
        }
        schoolBoard.getEntrance().extractStudent(student);
        schoolBoard.getDiningRoom().addStudent(student);
    }

    /**
     * method used to move a student from entrance to an island
     * @param student the chosen student
     * @param entrance the player's entrance
     * @param island the chosen island
     * @throws StudentNotExistingException exception thrown when the selected student is not in the entrance
     */
    public static void moveFromEntranceToIsland(Color student, Entrance entrance, Island island) throws StudentNotExistingException {
        entrance.extractStudent(student);
        island.addStudent(student);
    }
}
