package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.ActionStep2;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistant;
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
class ActionStep2Test {
    @Test
    public void testAction2_case1(){
        //Case1. Both players have no professors. No changes on the island
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

        assertEquals(1, game.getDashboard().getMotherNaturePosition());

        assertFalse(game.getDashboard().getIslands().get(1).hasTower());
    }

    // controllare i vari casi di resolve island



    @Test
    public void testActionStep2_case2(){
        //Case2: the island has a white tower. Black tower wins. Tower replaced.
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

        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(1).setTowers(Tower.WHITE);
        p2.getSchoolBoard().removeTower();

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


        assertEquals(1, game.getDashboard().getMotherNaturePosition());
        assertTrue(game.getDashboard().getIslands().get(1).hasTower());
        assertEquals(Tower.BLACK, game.getDashboard().getIslands().get(1).getTowerColor());
        assertEquals(7, p1.getSchoolBoard().getTowersNumber());
        assertEquals(8, p2.getSchoolBoard().getTowersNumber());
    }

    @Test
    public void testAction2_case3(){
        //Case3: the island has a white tower. Draw in influence count. No Changes.
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

        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        if (!(game.getDashboard().getIslands().get(1).getStudents().contains(Color.BLUE))){
        game.getDashboard().getIslands().get(1).addStudent(Color.BLUE);}
        game.getDashboard().getIslands().get(1).setTowers(Tower.WHITE);
        p2.getSchoolBoard().removeTower();

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

        assertEquals(1, game.getDashboard().getMotherNaturePosition());
        assertTrue(game.getDashboard().getIslands().get(1).hasTower());
        assertEquals(Tower.WHITE, game.getDashboard().getIslands().get(1).getTowerColor());
        assertEquals(8, p1.getSchoolBoard().getTowersNumber());
        assertEquals(7, p2.getSchoolBoard().getTowersNumber());
    }

    @Test
    public void testActionStep2_case4(){
        //Case 4: island 2 has a black Tower. Island 3 has a white Tower. Influence calculation on Island 3.
        //Black wins. Merge islands.
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
            p1.playAssistant(Assistant.ARCHER_CAT);
            p2.playAssistant(Assistant.DOG);}
        catch (AlreadyPlayedAssistant ignored){}

        p1.getSchoolBoard().getDiningRoom().addStudent(Color.BLUE);
        game.updateProfessors();

        game.getDashboard().getIslands().get(1).setTowers(Tower.BLACK);
        p1.getSchoolBoard().removeTower();
        game.getDashboard().getIslands().get(2).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(2).addStudent(Color.BLUE);
        game.getDashboard().getIslands().get(2).setTowers(Tower.WHITE);
        p2.getSchoolBoard().removeTower();

        game.setCurrentPlayer(p1);
        controller.setState(new ActionStep2(game, controller));
        controller.getState().execute();
        assertEquals(0, game.getDashboard().getMotherNaturePosition());

        //ActionStep2, Player1 active

        //Acks for getting state
        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        controller.getState().receiveMessage(new ChosenSteps(2), "Player1");

        controller.getState().receiveMessage(new Ack(), "Player1");
        controller.getState().receiveMessage(new Ack(), "Player2");

        assertEquals(2, game.getDashboard().getMotherNaturePosition());

        assertTrue(game.getDashboard().getIslands().get(1).hasTower());
        assertEquals(2, game.getDashboard().getIslands().get(1).getNumUnits());
        assertEquals(Tower.BLACK, game.getDashboard().getIslands().get(1).getTowerColor());
        assertEquals(8, p2.getSchoolBoard().getTowersNumber());
        assertEquals(6, p1.getSchoolBoard().getTowersNumber());
        assertEquals(11, game.getDashboard().getIslands().size());
    }
}
