package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.ActionStep3;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistant;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenCloud;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ActionStep3Test {
    @Test
    public void testAction3(){
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

        try{
            p1.playAssistant(Assistant.TIGER);
            p2.playAssistant(Assistant.OSTRICH);}
        catch (AlreadyPlayedAssistant ignored){}


        controller.updateTurnOrder();

        game.setCurrentPlayer(p1);
        controller.setState(new ActionStep3(game, controller));
        controller.getState().execute();

        Color c1 = game.getDashboard().getClouds().get(0).getStudents().get(0);
        Color c2 = game.getDashboard().getClouds().get(0).getStudents().get(1);
        Color c3 = game.getDashboard().getClouds().get(0).getStudents().get(2);

        //Acks for getting state
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        //chose cloud
        controller.getState().receiveMessage(new ChosenCloud(0), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        assertEquals(0, game.getDashboard().getClouds().get(0).getStudents().size());
        assertEquals(10, game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().size());
        assertSame(c1, game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().get(7));
        assertSame(c2, game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().get(8));
        assertSame(c3, game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().get(9));

        controller.getState().receiveMessage(new Ack(), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

    }
}
