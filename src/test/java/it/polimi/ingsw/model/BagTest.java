package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    public void bagInit() {
        Bag bag = new Bag();
        assertEquals(0, bag.getStudentsLeft());
    }

    @Test
    public void testRefill() {
        Bag bag = new Bag();

        bag.refill(2);
        assertEquals(2 * Color.values().length, bag.getStudentsLeft());
    }

    @Test
    public void testAddDrawStudent() {
        Bag bag = new Bag();

        assertEquals(0, bag.getStudentsLeft());
        bag.addStudent(new Student(Color.BLUE));
        assertEquals(1, bag.getStudentsLeft());
        assertEquals(Color.BLUE, bag.drawStudent().getColor());
    }
}