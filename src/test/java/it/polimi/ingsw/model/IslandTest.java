package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    @Test
    public void testInit() {
        Island island = new Island();

        assertEquals(1, island.getNumUnits());
        assertFalse(island.hasTower());
        assertNull(island.getTowerColor());
        assertFalse(island.useNoEntry());
    }

    @Test
    public void testInfluenceCount() {
        Island island = new Island();
        Tower blackTower = Tower.BLACK;
        Player player = new Player("Player");

        blackTower.addTeamComponent(player);
        for (int i = 0; i < 2; i++) {
            island.addStudent(Color.BLUE);
            island.addStudent(Color.GREEN);
        }
        assertEquals(0, island.countInfluence(blackTower));

        player.getSchoolBoard().addProfessor(new Professor(Color.GREEN));
        player.getSchoolBoard().addProfessor(new Professor(Color.PINK));
        assertEquals(2, island.countInfluence(blackTower));

        island.setTowers(Tower.BLACK);
        assertEquals(3, island.countInfluence(blackTower));

        Island newIsland = new Island();
        newIsland.addStudent(Color.PINK);
        island.addIsland(newIsland);
        assertEquals(5, island.countInfluence(blackTower));
    }

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

    @Test
    public void testNoEntry() {
        Island island1 = new Island();

        assertFalse(island1.useNoEntry());

        for (int i = 0; i < 2; i++) {
            island1.addNoEntry();
        }

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
}