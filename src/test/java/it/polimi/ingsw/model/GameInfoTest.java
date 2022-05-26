package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameInfoTest {

    @Test
    public void gameInfoTester(){

        GameInfo gameInfo = new GameInfo(23, 2, 4, true);

        assertEquals(23, gameInfo.getGameID());
        assertNotEquals(4, gameInfo.getNumOfWaitingPlayers());
        assertEquals(4, gameInfo.getNumOfTotalPlayers());
        assertTrue(gameInfo.isExpertGame());

        gameInfo.setNumOfWaitingPlayers(3);

        assertEquals(3, gameInfo.getNumOfWaitingPlayers());
    }
}
