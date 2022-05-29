package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.ActionStep2;
import it.polimi.ingsw.controller.states.ResolveIsland;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenSteps;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EndOfTheGameTest {

    @Test
    public void testEnding_case1(){
        //Player 1 has no towers left
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

        game.setCurrentPlayer(p1);

        while(game.getCurrentPlayer().getSchoolBoard().getTowersNumber()!=0){
            game.getCurrentPlayer().getSchoolBoard().removeTower();
        }
        controller.check();

        assertSame(p1, game.getWinners().get(0));
        assertEquals(1, game.getWinners().size());
    }

    @Test
    public void testEnding_case2(){
        //There are only 3 islands remaining. Player1 wins because has fewer towers left
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
            p2.playAssistant(Assistant.ARCHER_CAT);}
        catch (AlreadyPlayedAssistantException ignored){}

        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        game.getDashboard().getIslands().get(0).setTowers(Tower.BLACK);
        p1.getSchoolBoard().removeTower();

        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(1).setTowers(Tower.WHITE);
        p2.getSchoolBoard().removeTower();

        //islands 2-6 black
        game.getDashboard().getIslands().get(2).setTowers(Tower.BLACK);
        p1.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().get(2).addIsland(game.getDashboard().getIslands().get(3));
        p1.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().remove(3);
        game.getDashboard().getIslands().get(2).addIsland(game.getDashboard().getIslands().get(3));
        p1.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().remove(3);
        game.getDashboard().getIslands().get(2).addIsland(game.getDashboard().getIslands().get(3));
        p1.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().remove(3);
        game.getDashboard().getIslands().get(2).addIsland(game.getDashboard().getIslands().get(3));
        p1.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().remove(3);

        assertEquals(5, game.getDashboard().getIslands().get(2).getNumUnits());

        //island 9-12 white
        game.getDashboard().getIslands().get(4).setTowers(Tower.WHITE);
        p2.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().get(4).addIsland(game.getDashboard().getIslands().get(5));
        p2.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().remove(5);
        game.getDashboard().getIslands().get(4).addIsland(game.getDashboard().getIslands().get(5));
        p2.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().remove(5);
        game.getDashboard().getIslands().get(4).addIsland(game.getDashboard().getIslands().get(5));
        p2.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().remove(5);

        assertEquals(4, game.getDashboard().getIslands().get(4).getNumUnits());

        game.setCurrentPlayer(p1);
        controller.setState(new ActionStep2(game, controller));
        controller.getState().execute();
        assertEquals(0, game.getDashboard().getMotherNaturePosition());

        //ActionStep2, Player1 active

        //Acks for getting state
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        controller.getState().receiveMessage(new ChosenSteps(1), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");


        assertEquals(0, game.getDashboard().getMotherNaturePosition());

        assertEquals(Tower.BLACK, game.getDashboard().getIslands().get(0).getTowerColor());

        assertEquals(7, game.getDashboard().getIslands().get(0).getNumUnits());
        assertEquals(1, game.getDashboard().getIslands().get(1).getNumUnits());
        assertEquals(4, game.getDashboard().getIslands().get(2).getNumUnits());
        assertEquals(3, game.getDashboard().getIslands().size());

        assertEquals(1, p1.getSchoolBoard().getTowersNumber());
        assertEquals(4, p2.getSchoolBoard().getTowersNumber());

        assertSame(p1, game.getWinners().get(0));
        assertEquals(1, game.getWinners().size());
    }

    @Test
    public void testEnding_case3(){
        //Player BLACK has only 1 Tower left in Schoolboard. He wins an Island with 2 Towers on it.
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
            p2.playAssistant(Assistant.ARCHER_CAT);}
        catch (AlreadyPlayedAssistantException ignored){}

        //P1 has blue professor
        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        //Island 1 has Black Tower
        game.getDashboard().getIslands().get(0).setTowers(Tower.BLACK);
        p1.getSchoolBoard().removeTower();

        //Player 1 has 1 Tower left
        for (int i=0; i<6; i++){p1.getSchoolBoard().removeTower();}

        //Island 2 has 4 Blue students
        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);

        // Island 2 has White Tower
        game.getDashboard().getIslands().get(1).setTowers(Tower.WHITE);
        p2.getSchoolBoard().removeTower();

        //Island 3 has White Tower
        game.getDashboard().getIslands().get(2).setTowers(Tower.WHITE);
        p2.getSchoolBoard().removeTower();

        game.getDashboard().getIslands().get(1).addIsland(game.getDashboard().getIslands().get(2));

        game.setCurrentPlayer(p1);
        controller.setState(new ActionStep2(game, controller));
        controller.getState().execute();
        assertEquals(0, game.getDashboard().getMotherNaturePosition());

        //ActionStep2, Player1 active

        //Acks for getting state
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        controller.getState().receiveMessage(new ChosenSteps(1), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        assertEquals(Tower.BLACK, game.getDashboard().getIslands().get(0).getTowerColor());
        assertEquals(0, p1.getSchoolBoard().getTowersNumber());
        assertEquals(8, p2.getSchoolBoard().getTowersNumber());

        assertSame(p1, game.getWinners().get(0));
        assertEquals(1, game.getWinners().size());
    }
}
