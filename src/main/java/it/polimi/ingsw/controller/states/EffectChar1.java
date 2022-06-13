package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Char1;

/**
 * This state implements the effect of Character 1.
 * The player that activate this character will take one student from the character card ana place
 * it on an Island of choice. The Player will then draw a new student from the bag and place it on
 * the character card
 */
public class EffectChar1 implements State {

    Game game;
    Controller controller;
    ResumableState previousState;
    Char1 char1;
    boolean requestedAck = false;
    boolean requestedStudent = false;
    boolean requestedIsland = false;
    Color color;


    public EffectChar1(Game game, Controller controller, ResumableState previousState, Char1 char1) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char1 = char1;
    }

    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        requestedStudent=true;
        //pick a student on this card
        controller.notify(new MovableStudentsChar(char1.getStudents()));
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenStudent && requestedStudent) {
            receiveStudent((ChosenStudent) message);}
        else if (message instanceof ChosenDestination && requestedIsland){
            receiveIsland((ChosenDestination) message);}
        else if (message instanceof Ack && requestedAck){
            nextState();
        }

    }

    private void receiveStudent(ChosenStudent message) {
        Color student = message.getStudent();
        if (!(char1.getStudents().contains(student))) {
            controller.notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
        }
        else {
            this.color=student;
            requestedStudent=false;
            requestedIsland=true;
            //choose an island to move student on
            controller.notify(new WhereToMove());
        }
    }

    private void receiveIsland(ChosenDestination message){
        try {
            requestedIsland=false;
            //put the student on the island
            game.getDashboard().getIslands().get(message.getDestination() - 1).addStudent(color);
            //remove student from Char1
            char1.removeStudent(color);
            //draw a new student from the bag and place it on Char1
            char1.addStudent(game.getDashboard().getBag().drawStudent());
            //Send the updated board
            requestedAck=true;
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
            controller.notify(CommunicationMessage.SUCCESS);
        } catch (IndexOutOfBoundsException e) {
            controller.notify(ErrorMessage.INVALID_INPUT);
        }
    }
}
