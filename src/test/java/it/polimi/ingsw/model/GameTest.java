package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void testGetPlayer() {
        Game game = new Game(1, 2, true);
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        game.addNewPlayer(player1);
        game.addNewPlayer(player2);

        assertEquals(player1, game.getPlayer("Player1"));
        assertEquals(player2, game.getPlayer("Player2"));
        assertNotEquals(player1, game.getPlayer("Player2"));
        assertNotEquals(player2, game.getPlayer("Player1"));
        assertNull(game.getPlayer("Player3"));
    }

    @Test
    public void testPlayerTurns() {
        Game game = new Game(10, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");

        assertFalse(game.getOnlinePlayers().contains(p1));
        assertFalse(game.getOnlinePlayers().contains(p2));

        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        assertTrue(game.getOnlinePlayers().contains(p1));
        assertTrue(game.getOnlinePlayers().contains(p2));

        game.setCurrentPlayer(p1);
        assertEquals(p1, game.getCurrentPlayer());

        game.setNextPlayer();
        assertEquals(p2, game.getCurrentPlayer());

        game.setNextPlayer();
        assertEquals(p1, game.getCurrentPlayer());

        Player p3 = new Player("Player3");
        game.addNewPlayer(p3);

        game.setNextPlayer();
        game.setNextPlayer();
        assertEquals(p3, game.getCurrentPlayer());

        game.setNextPlayer();
        assertEquals(p1, game.getCurrentPlayer());
    }

    @Test
    public void testDealStudents() {
        Game game = new Game(3, 2, true);
        Player player = new Player("Player");

        game.getDashboard().getBag().refill(1);
        game.dealStudents(player, 5);

        while (player.getSchoolBoard().getFromEntrance().size() > 0) {
            player.getSchoolBoard().moveToDiningRoom(player.getSchoolBoard().getFromEntrance().get(0));
        }

        assertEquals(1, player.getSchoolBoard().getColor(Color.GREEN));
        assertEquals(1, player.getSchoolBoard().getColor(Color.RED));
        assertEquals(1, player.getSchoolBoard().getColor(Color.PINK));
        assertEquals(1, player.getSchoolBoard().getColor(Color.BLUE));
        assertEquals(1, player.getSchoolBoard().getColor(Color.YELLOW));
    }

    @Test
    public void testUpdateProfessors() {
        Game game = new Game(5, 2, false);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");

        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        p1.getSchoolBoard().addToEntrance(Color.GREEN);
        p1.getSchoolBoard().addToEntrance(Color.BLUE);
        p1.getSchoolBoard().addToEntrance(Color.RED);
        while (p1.getSchoolBoard().getFromEntrance().size() > 0) {
            p1.getSchoolBoard().moveToDiningRoom(p1.getSchoolBoard().getFromEntrance().get(0));
        }

        p2.getSchoolBoard().addToEntrance(Color.RED);
        p2.getSchoolBoard().addToEntrance(Color.PINK);
        p2.getSchoolBoard().addToEntrance(Color.GREEN);
        p2.getSchoolBoard().addToEntrance(Color.GREEN);
        while (p2.getSchoolBoard().getFromEntrance().size() > 0) {
            p2.getSchoolBoard().moveToDiningRoom(p2.getSchoolBoard().getFromEntrance().get(0));
        }

        game.updateProfessors();

        assertFalse(p1.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.YELLOW.ordinal()]));
        assertTrue(p1.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.BLUE.ordinal()]));
        assertFalse(p1.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.GREEN.ordinal()]));
        assertFalse(p1.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.RED.ordinal()]));
        assertFalse(p1.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.PINK.ordinal()]));

        assertFalse(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.YELLOW.ordinal()]));
        assertFalse(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.BLUE.ordinal()]));
        assertTrue(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.GREEN.ordinal()]));
        assertFalse(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.RED.ordinal()]));
        assertTrue(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.PINK.ordinal()]));

        p1.getSchoolBoard().addToEntrance(Color.PINK);
        p1.getSchoolBoard().addToEntrance(Color.PINK);
        while (p1.getSchoolBoard().getFromEntrance().size() > 0) {
            p1.getSchoolBoard().moveToDiningRoom(p1.getSchoolBoard().getFromEntrance().get(0));
        }
        game.updateProfessors();

        assertTrue(p1.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.PINK.ordinal()]));
        assertFalse(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.PINK.ordinal()]));
    }
}