package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test used to check the proper functioning of the Professor class
 */
class ProfessorTest {
    Professor professor = new Professor(Color.PINK);

    /**
     * test used to check the correct color of the professor
     */
    @Test
    public void testProfessorColor() {
        assertEquals(Color.PINK, professor.getColor());
    }

    /**
     * test used to check the owner of a professor
     */
    @Test
    public void testProfessorOwner() {
        Player playerTest = new Player("Player");
        assertNull(professor.getOwner());
        professor.changeOwner(playerTest);
        assertEquals(playerTest, professor.getOwner());
    }
}