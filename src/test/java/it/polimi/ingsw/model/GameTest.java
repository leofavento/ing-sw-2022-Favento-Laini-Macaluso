package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void testGetPlayer() {
        Game game = new Game(1, 2, true);
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        game.addNewPlayer(player1);
        game.addNewPlayer(player2);

        assertEquals(2, game.getNumberOfPlayers());
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

        try{
            player.getSchoolBoard().getDiningRoom().extractStudent(Color.GREEN);
        }
        catch  (StudentNotExistingException ignored){}
        try{
            player.getSchoolBoard().getDiningRoom().extractStudent(Color.GREEN);
        }
        catch  (StudentNotExistingException ignored){}

        player.getSchoolBoard().getDiningRoom().setEnableCoins();
        for (int i=0; i<4; i++){
        player.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);}
        assertEquals(1, player.getSchoolBoard().getCoins());
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
        try{
        p1.getSchoolBoard().getEntrance().extractStudent(Color.GREEN);}
        catch (StudentNotExistingException ignored){}
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

        Game game2= new Game(5, 3, false);

        Player p5 = new Player("Player5");
        Player p6 = new Player("Player6");
        Player p7 = new Player("Player7");

        game2.addNewPlayer(p5);
        game2.addNewPlayer(p6);
        game2.addNewPlayer(p7);

        game2.addPlayerToTeam(Tower.BLACK, p5);
        game2.addPlayerToTeam(Tower.WHITE, p6);
        game2.addPlayerToTeam(Tower.GREY, p7);

        assertEquals(p5, game2.getTeamFromTower(Tower.BLACK).get(0));

        for (Player player : game2.getOnlinePlayers()) {
            assertEquals(0, player.getSchoolBoard().getTowersNumber());
        }
        game2.initialTowersDeal();

        for (Tower tower : game2.getTeams()) {
            assertEquals(6, game2.getTeamFromTower(tower).get(0).getSchoolBoard().getTowersNumber());
        }
    }

    @Test
    public void roundTest(){
        Game game = new Game(10,2,false);

        assertFalse(game.getExpertGame());
        game.setExpertGame();
        assertTrue(game.getExpertGame());

        assertEquals(0, game.getRoundNumber());
        game.newRound();
        assertEquals(1, game.getRoundNumber());

        game.setFinalRound();
        assertTrue(game.getFinalRound());

        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        game.addNewPlayer(p1);
        game.addNewPlayer(p2);
        ArrayList<Player> winners = new ArrayList<>();
        winners.add(p1);

        assertEquals(0, game.getWinners().size());

        game.setWinners(winners);
        assertTrue(game.getWinners().contains(p1));

    }
}