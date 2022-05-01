package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.StudentDeposit;

import java.util.stream.IntStream;

public class Action {
    public static void moveFromBagToDeposit(Bag bag, StudentDeposit deposit, int number) {
        IntStream.range(0, number)
                .forEach(i -> deposit.addStudent(bag.drawStudent()));
    }
}
