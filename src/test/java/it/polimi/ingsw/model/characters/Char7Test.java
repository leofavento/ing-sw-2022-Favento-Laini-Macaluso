package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.ActionStep1;
import it.polimi.ingsw.exceptions.AlreadyPlayedCharacterException;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test used to verify the proper functioning of Character 7 effect
 */
class Char7Test {
    @Test
    public void testChar7() throws NotEnoughCoinsException, InvalidInputException, AlreadyPlayedCharacterException, StudentNotExistingException {
        Game game = new Game(1, 2, true);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        controller.getState().execute();

        CharacterController characterController = new CharacterController(controller, game);

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

        for (int i=0; i<7; i++){
            p1.getSchoolBoard().getEntrance().extractStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0));
        }

        p1.getSchoolBoard().getEntrance().addStudent(Color.BLUE);
        p1.getSchoolBoard().getEntrance().addStudent(Color.BLUE);


        game.setCurrentPlayer(p1);
        controller.setState(new ActionStep1(game, controller));
        //Char7 testing
        CharacterCard char7 = new Char7();
        ArrayList<CharacterCard> characterCards = new ArrayList<>();
        characterCards.add(char7);
        characterCards.add(null);
        characterCards.add(null);
        game.getDashboard().setCharacters(characterCards);
        characterController.setUpCharacter(char7, game.getDashboard().getBag());
        characterController.applyEffect(char7.getValue());
        assertTrue(char7.getActive());

        Color c1 = char7.getStudents().get(0);
        Color c2 = char7.getStudents().get(1);

        controller.getState().receiveMessage(new ChosenStudent(c1), "Player1");
        controller.getState().receiveMessage(new ChosenStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0)), "Player1");

        controller.getState().receiveMessage(new ChosenStudent(c2), "Player1");
        controller.getState().receiveMessage(new ChosenStudent(p1.getSchoolBoard().getEntrance().getStudents().get(0)), "Player1");

        controller.getState().receiveMessage(new ChosenStudent(null), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");

        assertTrue(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().contains(c1));
        assertTrue(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().contains(c2));

        //end of Char7 testing

    }
}
