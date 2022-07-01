package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test used to check the proper functioning of the Island class
 */
class IslandTest {
    /**
     * test used to check the correct initialization of an island
     */
    @Test
    public void testInit() {
        Island island = new Island();

        assertEquals(1, island.getNumUnits());
        assertFalse(island.hasTower());
        assertNull(island.getTowerColor());
        assertFalse(island.useNoEntry());
    }

    /**
     * test used to check if the influence count is working properly
     */
    @Test
    public void testInfluenceCount() {
        Island island = new Island();
        ArrayList<Player> team = new ArrayList<>();
        Player player = new Player("Player");
        player.getSchoolBoard().setTowerColor(Tower.BLACK);


        team.add(player);
        for (int i = 0; i < 2; i++) {
            island.addStudent(Color.BLUE);
            island.addStudent(Color.GREEN);
        }
        assertEquals(0, island.countInfluence(team));

        player.getSchoolBoard().addProfessor(new Professor(Color.GREEN));
        player.getSchoolBoard().addProfessor(new Professor(Color.PINK));
        assertEquals(2, island.countInfluence(team));

        island.setTowers(Tower.BLACK);
        assertEquals(3, island.countInfluence(team));

        Island newIsland = new Island();
        newIsland.addStudent(Color.PINK);
        island.addIsland(newIsland);
        assertEquals(5, island.countInfluence(team));
    }

    /**
     * test used to check if extra influence adds the right amount of influence
     */
    @Test
    public void testExtraInfluence() {
        Island island = new Island();
        Player player = new Player("Player");
        assertEquals(0, island.getExtraInfluence(player));

        island.setExtraInfluence(player, 3);
        assertEquals(3, island.getExtraInfluence(player));

        island.resetExtraInfluences();
        assertEquals(0, island.getExtraInfluence(player));
    }

    /**
     * test used to check the proper functioning of NoEntry tiles
     */
    @Test
    public void testNoEntry() {
        Island island1 = new Island();

        assertFalse(island1.useNoEntry());

        for (int i = 0; i < 2; i++) {
            island1.addNoEntry();
        }

        assertEquals(2, island1.getNoEntry());

        for (int i = 0; i < 2; i++) {
            assertTrue(island1.useNoEntry());
        }

        assertFalse(island1.useNoEntry());

        Island island2 = new Island();
        for (int i = 0; i < 2; i++) {
            island1.addNoEntry();
            island2.addNoEntry();
        }
        island1.addIsland(island2);

        for (int i = 0; i < 4; i++) {
            assertTrue(island1.useNoEntry());
        }

        assertFalse(island1.useNoEntry());
    }

    /**
     * test used to check if the correct amount of students are added or removed from an island
     */
    @Test
    public void testAddRemoveStudents() {
        Island island = new Island();

        for (Color color : Color.values()) {
            assertThrows(StudentNotExistingException.class, () -> island.extractStudent(color));
        }

        island.addStudent(Color.GREEN);
        island.addStudent(Color.GREEN);
        island.addStudent(Color.BLUE);

        assertDoesNotThrow(() -> island.extractStudent(Color.GREEN));
        assertDoesNotThrow(() -> island.extractStudent(Color.GREEN));
        assertThrows(StudentNotExistingException.class, () -> island.extractStudent(Color.GREEN));

        assertDoesNotThrow(() -> island.extractStudent(Color.BLUE));
        assertThrows(StudentNotExistingException.class, () -> island.extractStudent(Color.BLUE));
    }

    /**
     * test used to check if an island contains the specified students
     */
    @Test
    public void testGetStudents(){
        Island island=new Island();

        island.addStudent(Color.PINK);
        island.addStudent(Color.GREEN);

        ArrayList<Color> students= island.getStudents();

        assertTrue(students.contains(Color.PINK));
        assertTrue(students.contains(Color.GREEN));
        assertFalse(students.contains(Color.BLUE));
        assertFalse(students.contains(Color.RED));
        assertFalse(students.contains(Color.YELLOW));
    }
}