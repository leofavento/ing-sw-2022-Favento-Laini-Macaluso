package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.ActionStep1;
import it.polimi.ingsw.exceptions.AlreadyPlayedCharacterException;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.messages.fromClient.Ack;
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
 * Test used to verify the proper functioning of Character 2 effect
 */
class Char2Test {
    @Test
    public void testChar2() throws NotEnoughCoinsException, InvalidInputException, AlreadyPlayedCharacterException {
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

        assertEquals(1, p1.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.PINK)
                .count());
        assertEquals(1, p1.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.GREEN)
                .count());
        assertEquals(1, p1.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.BLUE)
                .count());
        assertEquals(1, p2.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.RED)
                .count());
        assertEquals(1, p2.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.YELLOW)
                .count());

        game.setCurrentPlayer(p2);

        controller.setState(new ActionStep1(game, controller));

        p2.getSchoolBoard().getDiningRoom().addStudent(Color.GREEN);
        game.updateProfessors();
        // char 2 not activated, so p2 doesn't control GREEN professor (both p1 and p2 have now 1 GREEN student)
        assertEquals(1, p1.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.GREEN)
                .count());
        assertEquals(0, p2.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.GREEN)
                .count());
        //Char2 testing
        CharacterCard char2 = new Char2();
        ArrayList<CharacterCard> characterCards = new ArrayList<>();
        characterCards.add(char2);
        characterCards.add(null);
        characterCards.add(null);
        game.getDashboard().setCharacters(characterCards);
        characterController.applyEffect(char2.getValue());
        assertTrue(char2.getActive());

        p2.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        p2.getSchoolBoard().getDiningRoom().addStudent(Color.PINK);
        game.updateProfessors();
        // p1 and p2 have both 2 PINK students but p2 activated Char2, so he controls PINK professor
        assertEquals(1, p2.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.PINK)
                .count());
        assertEquals(0, p1.getSchoolBoard().getProfessors().stream()
                .filter(p -> p.getColor() == Color.PINK)
                .count());

        //end of Char2 testing
    }

}
