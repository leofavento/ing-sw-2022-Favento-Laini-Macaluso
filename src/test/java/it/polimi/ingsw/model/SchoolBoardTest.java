package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {
    @Test
    public void testTowers() {
        SchoolBoard schoolBoard = new SchoolBoard();

        assertEquals(0, schoolBoard.getTowersNumber());
        assertNull(schoolBoard.getTowerColor());

        schoolBoard.setTowerColor(Tower.GREY);
        schoolBoard.setTowersNumber(6);

        assertEquals(Tower.GREY, schoolBoard.getTowerColor());
        assertEquals(6, schoolBoard.getTowersNumber());
    }

    @Test
    public void testCoins() {
        SchoolBoard schoolBoard = new SchoolBoard();

        assertEquals(0, schoolBoard.getCoins());

        for (int i = 0; i < 5; i++) {
            schoolBoard.addCoin();
        }
        assertEquals(5, schoolBoard.getCoins());

        schoolBoard.spendCoins(3);
        assertEquals(2, schoolBoard.getCoins());
    }

    @Test
    public void testEntrance() {
        SchoolBoard schoolBoard = new SchoolBoard();

        schoolBoard.addToEntrance(new Student(Color.BLUE));
        schoolBoard.addToEntrance(new Student(Color.BLUE));
        schoolBoard.addToEntrance(new Student(Color.RED));

        assertEquals(3, schoolBoard.getFromEntrance().size());
        schoolBoard.moveToDiningRoom(schoolBoard.getFromEntrance().get(0));
        assertEquals(2, schoolBoard.getFromEntrance().size());
        schoolBoard.moveToDiningRoom(schoolBoard.getFromEntrance().get(0));
        assertEquals(1, schoolBoard.getFromEntrance().size());
        schoolBoard.moveToDiningRoom(schoolBoard.getFromEntrance().get(0));
        assertEquals(0, schoolBoard.getFromEntrance().size());
    }
}