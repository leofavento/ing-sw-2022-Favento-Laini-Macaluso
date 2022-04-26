package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void testPlayedAssistant() {
        Player player = new Player("Player");

        assertEquals(10, player.getAvailableAssistants().size());

        assertEquals(0, player.getWizardID());
        player.setWizardID(2);
        assertEquals(2, player.getWizardID());

        try {
            player.playAssistant(Assistant.DOG);
        } catch (Exception ignored) {
        }

        assertEquals(9, player.getAvailableAssistants().size());
        assertFalse(player.getAvailableAssistants().contains(Assistant.DOG));
        assertEquals(Assistant.DOG, player.getPlayedAssistant());
    }

    @Test
    public void testPlayAssistantThrowsException() {
        Player player = new Player("Player");

        try {
            player.playAssistant(Assistant.DOG);
        } catch (Exception ignored) {
        }
        assertThrows(Exception.class, () -> player.playAssistant(Assistant.DOG));
    }

    @Test
    public void testGetFromCloud() {
        Player player = new Player("Player");
        Cloud cloud = new Cloud();

        cloud.addStudent(Color.BLUE);
        cloud.addStudent(Color.BLUE);
        cloud.addStudent(Color.YELLOW);

        assertNull(player.getChosenCloud());

        player.chooseCloud(cloud);
        assertEquals(cloud, player.getChosenCloud());

        for (Color student : player.getChosenCloud().getStudents()) {
            player.getSchoolBoard().addToEntrance(student);
        }
        assertEquals(3, player.getSchoolBoard().getFromEntrance().size());
    }
}