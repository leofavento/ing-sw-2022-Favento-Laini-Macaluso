package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.ActionStep1;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.model.characters.*;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    public void testFactory() {

        CharacterFactory characterFactory = new CharacterFactory();

        CharacterCard char1 = characterFactory.getCharacter(CharacterEnum.Char1);
        CharacterCard char2 = characterFactory.getCharacter(CharacterEnum.Char2);
        CharacterCard char3 = characterFactory.getCharacter(CharacterEnum.Char3);
        CharacterCard char4 = characterFactory.getCharacter(CharacterEnum.Char4);
        CharacterCard char5 = characterFactory.getCharacter(CharacterEnum.Char5);
        CharacterCard char6 = characterFactory.getCharacter(CharacterEnum.Char6);
        CharacterCard char7 = characterFactory.getCharacter(CharacterEnum.Char7);
        CharacterCard char8 = characterFactory.getCharacter(CharacterEnum.Char8);
        CharacterCard char9 = characterFactory.getCharacter(CharacterEnum.Char9);
        CharacterCard char10 = characterFactory.getCharacter(CharacterEnum.Char10);
        CharacterCard char11 = characterFactory.getCharacter(CharacterEnum.Char11);
        CharacterCard char12 = characterFactory.getCharacter(CharacterEnum.Char12);

        assertSame(char1.getValue(), CharacterEnum.Char1);
        assertSame(char2.getValue(), CharacterEnum.Char2);
        assertSame(char3.getValue(), CharacterEnum.Char3);
        assertSame(char4.getValue(), CharacterEnum.Char4);
        assertSame(char5.getValue(), CharacterEnum.Char5);
        assertSame(char6.getValue(), CharacterEnum.Char6);
        assertSame(char7.getValue(), CharacterEnum.Char7);
        assertSame(char8.getValue(), CharacterEnum.Char8);
        assertSame(char9.getValue(), CharacterEnum.Char9);
        assertSame(char10.getValue(), CharacterEnum.Char10);
        assertSame(char11.getValue(), CharacterEnum.Char11);
        assertSame(char12.getValue(), CharacterEnum.Char12);
    }

    @Test
    public void testChar1() throws NotEnoughCoinsException, InvalidInputException {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        CharacterController characterController = new CharacterController(controller, game, null);

        controller.getState().execute();

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        assertSame(Tower.BLACK, p1.getSchoolBoard().getTowerColor());
        assertSame(Tower.WHITE, p2.getSchoolBoard().getTowerColor());

        assertEquals(1, p1.getWizardID());
        assertEquals(2, p2.getWizardID());

        for (int i = 0; i < 7; i++) {
            p1.getSchoolBoard().addCoin();
            p2.getSchoolBoard().addCoin();
        }
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.GREEN);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.YELLOW);
        game.updateProfessors();



        controller.setState(new ActionStep1(game, controller));

        //Char1 testing
        CharacterCard char1 = new Char1();
        game.getDashboard().getBag().refill(10);

        char1.setUp(game.getDashboard().getBag());
        assertEquals(4, char1.getStudents().size());

        char1.removeStudent(char1.getStudents().get(0));
        assertEquals(3, char1.getStudents().size());

        for (int i=0; i<3; i++){
            char1.removeStudent(char1.getStudents().get(0));
        }

        for (int i=0; i<4; i++){
        char1.addStudent(Color.PINK);}
        assertTrue(char1.getStudents().contains(Color.PINK));

        characterController.applyEffect(char1);
        controller.getState().receiveMessage(new ChosenStudent(Color.PINK), "Player1");
        controller.getState().receiveMessage(new ChosenDestination(1), "Player1");
        assertTrue(char1.getActive());
        assertTrue(game.getDashboard().getIslands().get(0).getStudents().contains(Color.PINK));


        //end of Char1 testing

    }

    @Test
    public void testChar2() throws NotEnoughCoinsException, InvalidInputException {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        CharacterController characterController = new CharacterController(controller, game, null);

        controller.getState().execute();

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        assertSame(Tower.BLACK, p1.getSchoolBoard().getTowerColor());
        assertSame(Tower.WHITE, p2.getSchoolBoard().getTowerColor());

        assertEquals(1, p1.getWizardID());
        assertEquals(2, p2.getWizardID());

        for (int i = 0; i < 7; i++) {
            p1.getSchoolBoard().addCoin();
            p2.getSchoolBoard().addCoin();
        }
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.GREEN);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.YELLOW);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        game.setCurrentPlayer(p2);

        controller.setState(new ActionStep1(game, controller));
        //Char2 testing
        CharacterCard char2 = new Char2();

        characterController.applyEffect(char2);
        assertTrue(char2.getActive());

        for (int i=0; i<5; i++){
            if (game.getDashboard().getProfessors()[i].getColor()==Color.BLUE){
                assertTrue(p2.getSchoolBoard().getProfessors().contains(game.getDashboard().getProfessors()[i]));
            }
        }

        //end of Char2 testing
    }

    @Test
    public void testChar3() throws NotEnoughCoinsException, InvalidInputException {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        CharacterController characterController = new CharacterController(controller, game, null);

        controller.getState().execute();

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        assertSame(Tower.BLACK, p1.getSchoolBoard().getTowerColor());
        assertSame(Tower.WHITE, p2.getSchoolBoard().getTowerColor());

        assertEquals(1, p1.getWizardID());
        assertEquals(2, p2.getWizardID());

        for (int i = 0; i < 7; i++) {
            p1.getSchoolBoard().addCoin();
            p2.getSchoolBoard().addCoin();
        }
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.GREEN);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.YELLOW);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();


        assertSame(p1.getSchoolBoard().getProfessors().get(0).getColor(), Color.PINK);
        assertSame(p1.getSchoolBoard().getProfessors().get(1).getColor(), Color.GREEN);
        assertSame(p1.getSchoolBoard().getProfessors().get(2).getColor(), Color.BLUE);

        assertSame(p2.getSchoolBoard().getProfessors().get(0).getColor(), Color.RED);
        assertSame(p2.getSchoolBoard().getProfessors().get(1).getColor(), Color.YELLOW);
        //p1 has pink, green, blue professors.
        //p2 has red and yellow professors.



        game.getDashboard().getIslands().get(3).addStudent(Color.PINK);
        game.getDashboard().getIslands().get(3).addStudent(Color.PINK);
        game.getDashboard().getIslands().get(3).addStudent(Color.YELLOW);

        controller.setState(new ActionStep1(game, controller));

        //Char3 testing
        CharacterCard char3 = new Char3();
        characterController.applyEffect(char3);
        assertTrue(char3.getActive());

        controller.getState().receiveMessage(new ChosenDestination(4), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");

        assertTrue(game.getDashboard().getIslands().get(3).hasTower());
        assertSame(Tower.BLACK, game.getDashboard().getIslands().get(3).getTowerColor());

        //end of Char3 testing
    }

    @Test
    public void testChar4() throws NotEnoughCoinsException, InvalidInputException {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        CharacterController characterController = new CharacterController(controller, game, null);

        controller.getState().execute();

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        assertSame(Tower.BLACK, p1.getSchoolBoard().getTowerColor());
        assertSame(Tower.WHITE, p2.getSchoolBoard().getTowerColor());

        assertEquals(1, p1.getWizardID());
        assertEquals(2, p2.getWizardID());

        for (int i = 0; i < 7; i++) {
            p1.getSchoolBoard().addCoin();
            p2.getSchoolBoard().addCoin();
        }
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.GREEN);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.YELLOW);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        controller.setState(new ActionStep1(game, controller));
        //Char4 testing
        CharacterCard char4 = new Char4();

        characterController.applyEffect(char4);
        assertTrue(char4.getActive());

        assertEquals(2, game.getDashboard().getAdditionalMNMovements());

        //end of Char4 testing

    }

    @Test
    public void testChar5() throws NotEnoughCoinsException, InvalidInputException {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        CharacterController characterController = new CharacterController(controller, game, null);

        controller.getState().execute();

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        assertSame(Tower.BLACK, p1.getSchoolBoard().getTowerColor());
        assertSame(Tower.WHITE, p2.getSchoolBoard().getTowerColor());

        assertEquals(1, p1.getWizardID());
        assertEquals(2, p2.getWizardID());

        for (int i = 0; i < 7; i++) {
            p1.getSchoolBoard().addCoin();
            p2.getSchoolBoard().addCoin();
        }
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.GREEN);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.YELLOW);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        controller.setState(new ActionStep1(game, controller));
        //Char5 testing
        CharacterCard char5 = new Char5();

        characterController.applyEffect(char5);
        assertTrue(char5.getActive());

        controller.getState().receiveMessage(new ChosenDestination(3), "Player1");

        assertEquals(1, game.getDashboard().getIslands().get(2).getNoEntry());

        char5.addNoEntryTile();

        for (int i=0; i<6; i++){
        try{
        char5.useNoEntryTile();}
        catch (NoEntryTilesLeftException ignored){}
        }

        //end of Char5 testing

    }

    @Test
    public void testChar6() throws NotEnoughCoinsException, InvalidInputException {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        CharacterController characterController = new CharacterController(controller, game, null);

        controller.getState().execute();

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        assertSame(Tower.BLACK, p1.getSchoolBoard().getTowerColor());
        assertSame(Tower.WHITE, p2.getSchoolBoard().getTowerColor());

        assertEquals(1, p1.getWizardID());
        assertEquals(2, p2.getWizardID());

        for (int i = 0; i < 7; i++) {
            p1.getSchoolBoard().addCoin();
            p2.getSchoolBoard().addCoin();
        }
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.GREEN);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.YELLOW);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        controller.setState(new ActionStep1(game, controller));
        //Char6 testing
        CharacterCard char6 = new Char6();

        characterController.applyEffect(char6);
        assertTrue(char6.getActive());
        assertTrue(game.getDashboard().getDoNotCountTowers());

        //end of Char6 testing
    }

    @Test
    public void testChar7() throws NotEnoughCoinsException, InvalidInputException {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        controller.getState().execute();

        CharacterController characterController = new CharacterController(controller, game, null);

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        assertSame(Tower.BLACK, p1.getSchoolBoard().getTowerColor());
        assertSame(Tower.WHITE, p2.getSchoolBoard().getTowerColor());

        assertEquals(1, p1.getWizardID());
        assertEquals(2, p2.getWizardID());

        for (int i = 0; i < 7; i++) {
            p1.getSchoolBoard().addCoin();
            p2.getSchoolBoard().addCoin();
        }
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.GREEN);
        game.updateProfessors();
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.RED);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.YELLOW);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        p1.getSchoolBoard().getEntrance().addStudent(Color.BLUE);
        p1.getSchoolBoard().getEntrance().addStudent(Color.BLUE);


        controller.setState(new ActionStep1(game, controller));
        //Char7 testing
        CharacterCard char7 = new Char7();
        characterController.applyEffect(char7);
        assertTrue(char7.getActive());

        characterController.setUpCharacter(char7, game.getDashboard().getBag());
        System.out.println(char7.getStudents().toString());

        Color c1 = char7.getStudents().get(0);
        Color c2 = char7.getStudents().get(1);


        controller.getState().receiveMessage(new ChosenStudent(char7.getStudents().get(0)), "Player1");
        controller.getState().receiveMessage(new ChosenStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0)), "Player1");

        controller.getState().receiveMessage(new ChosenStudent(char7.getStudents().get(0)), "Player1");
        controller.getState().receiveMessage(new ChosenStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0)), "Player1");

        controller.getState().receiveMessage(new ChosenStudent(null), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");

        assertTrue(p1.getSchoolBoard().getEntrance().getStudents().contains(c1));
        assertTrue(p1.getSchoolBoard().getEntrance().getStudents().contains(c2));

        //end of Char7 testing

    }




}
