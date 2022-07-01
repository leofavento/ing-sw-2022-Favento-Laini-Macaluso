package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests used to verify the proper functioning of the Bag
 */
class BagTest {

    /**
     * Test used to verify that a
     */
    @Test
    public void bagInit() {
        Bag bag = new Bag();
        assertEquals(0, bag.getStudentsLeft());
    }

    /**
     * Test used to verify the correct refill of the Bag
     */
    @Test
    public void testRefill() {
        Bag bag = new Bag();

        bag.refill(2);
        assertEquals(2 * Color.values().length, bag.getStudentsLeft());
    }

    /**
     * Test used to verify the correct draw of the students from the Bag
     */
    @Test
    public void testAddDrawStudent() {
        Bag bag = new Bag();

        assertEquals(0, bag.getStudentsLeft());
        bag.addStudent(Color.BLUE);
        assertEquals(1, bag.getStudentsLeft());
        assertEquals(Color.BLUE, bag.drawStudent());
    }
}