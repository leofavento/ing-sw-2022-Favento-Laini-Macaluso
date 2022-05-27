package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.ActionStep1;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistant;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ActionStep1Test {
    @Test
    public void testAction1(){
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

        p1.getSchoolBoard().getEntrance().addStudent(Color.BLUE);
        p1.getSchoolBoard().getEntrance().addStudent(Color.RED);
        p1.getSchoolBoard().getEntrance().addStudent(Color.GREEN);

       game.setCurrentPlayer(p1);
        controller.setState(new ActionStep1(game, controller));
        controller.getState().execute();

        //ActionStep1, Player1 active

        //Acks for getting state
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        //Move Blue to DiningRoom
        controller.getState().receiveMessage(new ChosenStudent(Color.BLUE), "Player1");
        controller.getState().receiveMessage(new ChosenDestination(0), "Player1");

        //Acks for updated board
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        //Move Red to Island 3
        controller.getState().receiveMessage(new ChosenStudent(Color.RED), "Player1");
        controller.getState().receiveMessage(new ChosenDestination(3), "Player1");

        //Acks for updated board
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        //Move Green to Island 10
        controller.getState().receiveMessage(new ChosenStudent(Color.GREEN), "Player1");
        controller.getState().receiveMessage(new ChosenDestination(10), "Player1");

        //Acks for updated board
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        assertEquals(1, game.getCurrentPlayer().getSchoolBoard().getDiningRoom().getStudentsNumber(Color.BLUE));

        assertTrue(game.getDashboard().getIslands().get(2).getStudents().contains(Color.RED));

        assertTrue(game.getDashboard().getIslands().get(9).getStudents().contains(Color.GREEN));


    }
}
