package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.ActionStep1;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Char1Test {

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
}
