package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test used to verify the correct functioning of the player's actions and status
 */
class PlayerTest {
    /**
     * test used to check the proper functioning of the choice of the assisstant
     */
    @Test
    public void testPlayedAssistant() {
        Player player = new Player("Player");

        assertEquals(10, player.getAvailableAssistants().size());

        assertEquals(0, player.getWizardID());
        player.setWizardID(2);
        assertEquals(2, player.getWizardID());

        try {
            player.playAssistant(Assistant.DOG);
        } catch (AlreadyPlayedAssistantException ignored) {}

        try {
            player.playAssistant(Assistant.DOG);
        } catch (AlreadyPlayedAssistantException ignored) {}

        assertEquals(9, player.getAvailableAssistants().size());
        assertFalse(player.getAvailableAssistants().contains(Assistant.DOG));
        assertEquals(Assistant.DOG, player.getPlayedAssistant());
    }

    /**
     * tests if all the students from an island get moved to the player's Entrance
     */
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
            player.getSchoolBoard().getEntrance().addStudent(student);
        }
        assertEquals(3, player.getSchoolBoard().getEntrance().getStudents().size());
    }

    /**
     * tests the proper functioning of the setStatus method
     */
    @Test
    public void testStatus(){
        Player player = new Player("Player1");

        player.setStatus(PlayerStatus.WAITING);
        assertSame(player.getStatus(), PlayerStatus.WAITING);

        player.setStatus(PlayerStatus.PLANNING);
        assertSame(player.getStatus(), PlayerStatus.PLANNING);

        player.setStatus(PlayerStatus.MOVE_1);
        assertSame(player.getStatus(), PlayerStatus.MOVE_1);

        player.setStatus(PlayerStatus.MOVE_2);
        assertSame(player.getStatus(), PlayerStatus.MOVE_2);

        player.setStatus(PlayerStatus.MOVE_3);
        assertSame(player.getStatus(), PlayerStatus.MOVE_3);

        player.setStatus(PlayerStatus.END_MOVE_1);
        assertSame(player.getStatus(), PlayerStatus.END_MOVE_1);

        player.setStatus(PlayerStatus.END_MOVE_2);
        assertSame(player.getStatus(), PlayerStatus.END_MOVE_2);
    }
}