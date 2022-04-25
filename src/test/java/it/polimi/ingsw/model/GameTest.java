package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void testDealStudents() {
        Game game = new Game();
        Player player = new Player();

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
        Game game = new Game();
        Player p1 = new Player();
        Player p2 = new Player();

        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        p1.getSchoolBoard().addToEntrance(new Student(Color.GREEN));
        p1.getSchoolBoard().addToEntrance(new Student(Color.BLUE));
        p1.getSchoolBoard().addToEntrance(new Student(Color.RED));
        while (p1.getSchoolBoard().getFromEntrance().size() > 0) {
            p1.getSchoolBoard().moveToDiningRoom(p1.getSchoolBoard().getFromEntrance().get(0));
        }

        p2.getSchoolBoard().addToEntrance(new Student(Color.RED));
        p2.getSchoolBoard().addToEntrance(new Student(Color.PINK));
        p2.getSchoolBoard().addToEntrance(new Student(Color.GREEN));
        p2.getSchoolBoard().addToEntrance(new Student(Color.GREEN));
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

        p1.getSchoolBoard().addToEntrance(new Student(Color.PINK));
        p1.getSchoolBoard().addToEntrance(new Student(Color.PINK));
        while (p1.getSchoolBoard().getFromEntrance().size() > 0) {
            p1.getSchoolBoard().moveToDiningRoom(p1.getSchoolBoard().getFromEntrance().get(0));
        }
        game.updateProfessors();

        assertTrue(p1.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.PINK.ordinal()]));
        assertFalse(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[Color.PINK.ordinal()]));
    }
}