package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {
    Professor professor = new Professor(Color.PINK);

    @Test
    public void testProfessorColor() {
        assertEquals(Color.PINK, professor.getColor());
    }

    @Test
    public void testProfessorOwner() {
        Player playerTest = new Player();
        assertNull(professor.getOwner());
        professor.changeOwner(playerTest);
        assertEquals(playerTest, professor.getOwner());
    }
}