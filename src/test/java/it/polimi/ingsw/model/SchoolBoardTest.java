package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.player.SchoolBoard;
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
    public void testEntrance() throws StudentNotExistingException {
        SchoolBoard schoolBoard = new SchoolBoard();

        schoolBoard.getEntrance().addStudent(Color.BLUE);
        schoolBoard.getEntrance().addStudent(Color.BLUE);
        schoolBoard.getEntrance().addStudent(Color.RED);

        assertEquals(3, schoolBoard.getEntrance().getStudents().size());
        schoolBoard.getDiningRoom().addStudent(schoolBoard.getEntrance().getStudents().get(0));
        schoolBoard.getEntrance().extractStudent(schoolBoard.getEntrance().getStudents().get(0));
        assertEquals(2, schoolBoard.getEntrance().getStudents().size());
        schoolBoard.getDiningRoom().addStudent(schoolBoard.getEntrance().getStudents().get(0));
        schoolBoard.getEntrance().extractStudent(schoolBoard.getEntrance().getStudents().get(0));
        assertEquals(1, schoolBoard.getEntrance().getStudents().size());
        schoolBoard.getDiningRoom().addStudent(schoolBoard.getEntrance().getStudents().get(0));
        schoolBoard.getEntrance().extractStudent(schoolBoard.getEntrance().getStudents().get(0));
        assertEquals(0, schoolBoard.getEntrance().getStudents().size());
    }
}