package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.player.Player;
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
    public void testDealStudents() throws StudentNotExistingException {
        Game game = new Game(3, 2, true);
        Player player = new Player("Player");

        game.getDashboard().getBag().refill(1);
        game.dealStudents(player, 5);

        while (player.getSchoolBoard().getEntrance().getStudents().size() > 0) {
            player.getSchoolBoard().getDiningRoom().addStudent(player.getSchoolBoard().getEntrance().getStudents().get(0));
            player.getSchoolBoard().getEntrance().extractStudent(player.getSchoolBoard().getEntrance().getStudents().get(0));

        }

        assertEquals(1, player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.GREEN));
        assertEquals(1, player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.RED));
        assertEquals(1, player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.PINK));
        assertEquals(1, player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.BLUE));
        assertEquals(1, player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.YELLOW));
    }

    @Test
    public void testUpdateProfessors() throws StudentNotExistingException {
        Game game = new Game(5, 2, false);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");

        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        p1.getSchoolBoard().getEntrance().addStudent(Color.GREEN);
        p1.getSchoolBoard().getEntrance().addStudent(Color.BLUE);
        p1.getSchoolBoard().getEntrance().addStudent(Color.RED);
        while (p1.getSchoolBoard().getEntrance().getStudents().size() > 0) {
            p1.getSchoolBoard().getDiningRoom().addStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0));
            p1.getSchoolBoard().getEntrance().extractStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0));
        }

        p2.getSchoolBoard().getEntrance().addStudent(Color.RED);
        p2.getSchoolBoard().getEntrance().addStudent(Color.PINK);
        p2.getSchoolBoard().getEntrance().addStudent(Color.GREEN);
        p2.getSchoolBoard().getEntrance().addStudent(Color.GREEN);
        while (p2.getSchoolBoard().getEntrance().getStudents().size() > 0) {
            p2.getSchoolBoard().getDiningRoom().addStudent(p2.getSchoolBoard().getEntrance().getStudents().get(0));
            p2.getSchoolBoard().getEntrance().extractStudent(p2.getSchoolBoard().getEntrance().getStudents().get(0));
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

        p1.getSchoolBoard().getEntrance().addStudent(Color.PINK);
        p1.getSchoolBoard().getEntrance().addStudent(Color.PINK);
        while (p1.getSchoolBoard().getEntrance().getStudents().size() > 0) {
            p1.getSchoolBoard().getDiningRoom().addStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0));
            p1.getSchoolBoard().getEntrance().extractStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0));
        }
        game.updateProfessors();

        assertTrue(p1.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.PINK.ordinal()]));
        assertFalse(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.PINK.ordinal()]));
    }

    @Test
    public void testTowersDeal() {
        Game game = new Game(3, 2, false);

        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        Player p3 = new Player("Player3");
        Player p4 = new Player("Player4");

        game.addNewPlayer(p1);
        game.addNewPlayer(p2);
        game.addNewPlayer(p3);
        game.addNewPlayer(p4);
        game.addPlayerToTeam(Tower.BLACK, p1);
        game.addPlayerToTeam(Tower.WHITE, p2);
        game.addPlayerToTeam(Tower.BLACK, p3);
        game.addPlayerToTeam(Tower.WHITE, p4);

        assertEquals(p1, game.getTeamFromTower(Tower.BLACK).get(0));
        assertEquals(game.getTeamFromTower(Tower.WHITE), game.getTeamFromPlayer(p4));

        for (Player player : game.getOnlinePlayers()) {
            assertEquals(0, player.getSchoolBoard().getTowersNumber());
        }
        game.initialTowersDeal();

        for (Tower tower : game.getTeams()) {
            assertEquals(8, game.getTeamFromTower(tower).get(0).getSchoolBoard().getTowersNumber());
            assertEquals(0, game.getTeamFromTower(tower).get(1).getSchoolBoard().getTowersNumber());
        }
    }
}