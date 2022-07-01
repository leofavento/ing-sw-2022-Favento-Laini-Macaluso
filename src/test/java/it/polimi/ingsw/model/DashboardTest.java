package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.AlreadyPlayedCharacterException;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.model.characters.Char1;
import it.polimi.ingsw.model.characters.Char5;
import it.polimi.ingsw.model.characters.Char9;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    /**
     * Test used to verify the correct placement of the islands in the Dashboard
     */
    @Test
    public void testPlaceIslands() {
        Dashboard dashboard = new Dashboard();

        assertEquals(0, dashboard.getIslands().size());
        dashboard.placeIslands();
        assertEquals(12, dashboard.getIslands().size());
    }

    /**
     * Test used to verify the proper functioning of the movements of mother nature
     */
    @Test
    public void testMotherNature() {
        Dashboard dashboard = new Dashboard();

        dashboard.placeIslands();
        dashboard.setMotherNature(dashboard.getIslands().get(1));
        assertEquals(1, dashboard.getMotherNaturePosition());

        dashboard.moveMotherNature(15);
        assertEquals(4, dashboard.getMotherNaturePosition());

        dashboard.mergeIslands(dashboard.getIslands().get(4), dashboard.getIslands().get(3), dashboard.getIslands().get(5));
        assertEquals(3, dashboard.getMotherNaturePosition());
        dashboard.moveMotherNature(11);
        assertEquals(4, dashboard.getMotherNaturePosition());

        dashboard.setAdditionalMNMovements(2);
        assertEquals(2, dashboard.getAdditionalMNMovements());
    }

    /**
     * Test used to verify the proper functioning of Clouds
     */
    @Test
    public void testClouds() {
        Dashboard dashboard = new Dashboard();

        dashboard.placeCloudTiles(2);
        assertArrayEquals(new Color[]{}, dashboard.getClouds().get(0).getStudents().toArray());
        assertArrayEquals(new Color[]{}, dashboard.getClouds().get(1).getStudents().toArray());

        Color student1 = Color.GREEN;
        Color student2 = Color.PINK;
        Color student3 = Color.PINK;
        Color student4 = Color.RED;
        Color student5 = Color.BLUE;

        dashboard.getClouds().get(0).addStudent(student1);
        dashboard.getClouds().get(0).addStudent(student2);
        dashboard.getClouds().get(0).addStudent(student3);
        dashboard.getClouds().get(1).addStudent(student4);
        dashboard.getClouds().get(1).addStudent(student5);

        assertArrayEquals(new Color[]{student1, student2, student3}, dashboard.getClouds().get(0).getStudents().toArray());
        assertArrayEquals(new Color[]{student4, student5}, dashboard.getClouds().get(1).getStudents().toArray());

        assertThrows(StudentNotExistingException.class, () -> dashboard.getClouds().get(0).extractStudent(Color.YELLOW));
        assertDoesNotThrow(() -> dashboard.getClouds().get(0).extractStudent(Color.PINK));
        assertArrayEquals(new Color[]{student1, Color.PINK}, dashboard.getClouds().get(0).getStudents().toArray());
    }

    /**
     * Test used to verify the proper placements of towers on islands
     */
    @Test
    public void testCountTowers() {
        Dashboard dashboard = new Dashboard();
        Tower tower = Tower.BLACK;

        dashboard.placeIslands();

        assertEquals(0, dashboard.countTowers(tower));

        dashboard.getIslands().get(3).setTowers(Tower.BLACK);
        dashboard.getIslands().get(6).setTowers(Tower.WHITE);
        dashboard.getIslands().get(1).setTowers(Tower.BLACK);
        dashboard.getIslands().get(8).setTowers(Tower.GREY);
        assertEquals(2, dashboard.countTowers(tower));

        dashboard.getIslands().get(3).setTowers(Tower.GREY);
        assertEquals(1, dashboard.countTowers(tower));

        dashboard.setDoNotCountTowers(true);
        assertTrue(dashboard.getDoNotCountTowers());
    }

    /**
     * Test used to verify the proper initialization of the characters
     */
    @Test
    public void testCharactersInitialization() {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        CharacterController characterController = new CharacterController(controller, game);

        controller.getState().execute();

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        ArrayList<CharacterCard> array = new ArrayList<>();
        CharacterCard c1 = new Char1();
        array.add(c1);
        CharacterCard c9 = new Char9();
        array.add(c9);
        CharacterCard c5 = new Char5();
        array.add(c5);
        game.getDashboard().setCharacters(array);

        for (int i = 0; i < 7; i++) {
            p1.getSchoolBoard().addCoin();
            p2.getSchoolBoard().addCoin();
        }

        game.setCurrentPlayer(p1);

        assertEquals(3, game.getDashboard().getCharacters().length);
        assertNotEquals(game.getDashboard().getCharacters()[0], game.getDashboard().getCharacters()[1]);
        assertNotEquals(game.getDashboard().getCharacters()[0], game.getDashboard().getCharacters()[2]);
        assertNotEquals(game.getDashboard().getCharacters()[1], game.getDashboard().getCharacters()[2]);

        try{
        characterController.applyEffect(c5.getValue());}
        catch (NotEnoughCoinsException | InvalidInputException | AlreadyPlayedCharacterException ignored){}

        for (CharacterCard card : game.getDashboard().getCharacters()) {
            if (card instanceof Char5) {
                assertTrue(card.getActive());
            } else {
                assertFalse(card.getActive());
            }
        }
    }

    /**
     * Test used to verify the correct avoided count of a selected color
     */
    @Test
    public void DoNotCountColorTest(){
        Dashboard dashboard = new Dashboard();

        dashboard.setDoNotCountColor(Color.BLUE);
        assertNotSame(dashboard.getDoNotCountColor(), Color.PINK);
        dashboard.resetDoNotCountColor();
        assertNull(dashboard.getDoNotCountColor());
    }
}