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

public class Action {
    public static void moveFromBagToDeposit(Bag bag, StudentDeposit deposit, int number) {
        IntStream.range(0, number)
                .forEach(i -> deposit.addStudent(bag.drawStudent()));
    }

    public static void moveFromEntranceToDining(Color student, SchoolBoard schoolBoard) throws FullDiningRoomException, StudentNotExistingException {
        if (schoolBoard.getDiningRoom().getStudentsNumber(student) == 10) {
            throw new FullDiningRoomException("The dining room is full for " + student.name() + " students.");
        }
        schoolBoard.getEntrance().extractStudent(student);
        schoolBoard.getDiningRoom().addStudent(student);
        if (schoolBoard.getDiningRoom().getStudentsNumber(student) % 3 == 0) {
            schoolBoard.addCoin();
        }
    }

    public static void moveFromEntranceToIsland(Color student, Entrance entrance, Island island) throws StudentNotExistingException {
        entrance.extractStudent(student);
        island.addStudent(student);
    }
}
