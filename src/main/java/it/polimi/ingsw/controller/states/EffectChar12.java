package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.SelectColor;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Char12;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class EffectChar12 implements ResumableState{

    Game game;
    Controller controller;
    ResumableState previousState;
    Char12 char12;
    boolean requestedAck = false;
    boolean requestedColor = false;
    Color color;

    public EffectChar12(Game game, Controller controller, ResumableState previousState, Char12 char12) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char12 = char12;
    }

    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        requestedColor=true;
        controller.notify(new SelectColor());
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenStudent && requestedColor){
            this.color=((ChosenStudent) message).getStudent();
            requestedColor=false;
            returnStudents();
        }
        else if (message instanceof Ack && requestedAck){
            requestedAck=false;
            nextState();
        }
    }

    private void returnStudents(){
        for (Player player:game.getOnlinePlayers()) {
            for (int i=0; i<3; i++) {
                try{
                player.getSchoolBoard().getDiningRoom().extractStudent(color);
                game.getDashboard().getBag().addStudent(color);
                }
                catch (StudentNotExistingException e){
                    i=3;}
            }
        }
        requestedAck=true;
        controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
    }


    @Override
    public void resume() {

    }
}
