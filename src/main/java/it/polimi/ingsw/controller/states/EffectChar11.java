package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Char11;

/**
 * This state implements the effect of Character 11.
 * The player that activate this character will take 1 student from the character card and place it in
 * the Dining Room.
 * Then, the player will draw a new student from the bag and place it on the character card.
 */
public class EffectChar11 implements State {

    Game game;
    Controller controller;
    ResumableState previousState;
    Char11 char11;
    boolean requestedAck = false;
    boolean requestedStudent = false;
    Color color;


    public EffectChar11(Game game, Controller controller, ResumableState previousState, Char11 char11) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char11 = char11;
    }

    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        requestedStudent = true;
        //pick a student on this card
        controller.notify(new MovableStudentsChar(char11.getStudents()));
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenStudent && requestedStudent) {
            receiveStudent((ChosenStudent) message);
        } else if (message instanceof Ack && requestedAck) {
            nextState();
        }
    }

    private void receiveStudent(ChosenStudent message) {
        Color student = message.getStudent();
        if (!(char11.getStudents().contains(student))) {
            controller.notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
        } else {
            this.color = student;
            requestedStudent = false;
            game.getCurrentPlayer().getSchoolBoard().getDiningRoom().addStudent(color);
            char11.removeStudent(color);
            char11.setUp(game.getDashboard().getBag());
            requestedAck = true;
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
            controller.notify(CommunicationMessage.SUCCESS);
        }
    }
}
