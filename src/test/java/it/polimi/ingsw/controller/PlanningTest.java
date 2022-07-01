package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.messages.fromClient.PlayAssistant;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test used to simulate the Planning phase of a generic turn.
 */
class PlanningTest {
    @Test
    public void testPlanning(){
        Game game = new Game(1, 2, false);
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);

        Controller controller = new Controller(game);
        controller.getState().execute();

        controller.getState().receiveMessage(new ChosenTower(Tower.BLACK),"Player1");
        controller.getState().receiveMessage(new ChosenTower(Tower.WHITE),"Player2");

        controller.getState().receiveMessage(new ChosenWizard(1), "Player1");
        controller.getState().receiveMessage(new ChosenWizard(2), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        //Planning State
        game.setCurrentPlayer(p1);

        //Acks for getting state
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        //Player1 sending assistant
        controller.getState().receiveMessage(new PlayAssistant(Assistant.OSTRICH), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player1");

        //Acks for getting state
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        //Player2 sending assistant
        controller.getState().receiveMessage(new PlayAssistant(Assistant.TIGER), "Player2");
        controller.getState().receiveMessage(new Ack(), "Player2");

        //Acks for updated board
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        //Acks for getting state
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        assertSame(p2, game.getCurrentPlayer());

        assertSame(Assistant.OSTRICH, p1.getPlayedAssistant());
        assertSame(Assistant.TIGER, p2.getPlayedAssistant());
    }
}
