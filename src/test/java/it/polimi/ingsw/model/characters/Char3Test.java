package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.ActionStep1;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Char3Test {
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



        game.getDashboard().getIslands().get(0).addStudent(Color.PINK);
        game.getDashboard().getIslands().get(0).addStudent(Color.PINK);
        game.getDashboard().getIslands().get(0).addStudent(Color.YELLOW);

        controller.setState(new ActionStep1(game, controller));

        //Char3 testing
        CharacterCard char3 = new Char3();
        characterController.applyEffect(char3);
        assertTrue(char3.getActive());

        controller.getState().receiveMessage(new ChosenDestination(1), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");

        assertTrue(game.getDashboard().getIslands().get(0).hasTower());
        assertSame(Tower.BLACK, game.getDashboard().getIslands().get(0).getTowerColor());

        //end of Char3 testing
    }
}
