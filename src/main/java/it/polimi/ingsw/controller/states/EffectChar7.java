package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Char7;

/**
 * This state implements the effect of Character 7.
 * The player that activate this character can take up to 3 students from the character card and replace them with the
 * same number of students from the entrance.
 */
public class EffectChar7 implements State {

    Game game;
    Controller controller;
    ResumableState previousState;
    Char7 char7;
    boolean requestedAck = false;
    boolean requestedCharStudent = false;
    boolean requestedEntranceStudent = false;
    int i = 0;
    Color charStudent;
    Color entranceStudent;

    public EffectChar7(Game game, Controller controller, ResumableState previousState, Char7 char7) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char7 = char7;
    }

    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        chooseStudentFromChar();
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenStudent && requestedCharStudent && !requestedEntranceStudent) {
            if (((ChosenStudent) message).getStudent() != null) {
                this.charStudent = ((ChosenStudent) message).getStudent();
                requestedCharStudent = false;
                chooseStudentFromEntrance();
            } else {
                i = 3;
                requestedCharStudent = false;
                chooseStudentFromChar();
            }
        } else if (message instanceof ChosenStudent && requestedEntranceStudent && !requestedCharStudent) {
            this.entranceStudent = ((ChosenStudent) message).getStudent();
            requestedEntranceStudent = false;
            if (entranceStudent == null) {
                controller.notify(ErrorMessage.INVALID_INPUT);
                chooseStudentFromChar();
            } else {
                swapStudents();
            }
        } else if (message instanceof Ack && requestedAck) {
            requestedAck = false;
            nextState();
        }
    }

    private void chooseStudentFromChar() {
        requestedCharStudent = true;
        if (i < 3) {
            controller.notify(new MovableStudentsChar(char7.getStudents()));
        } else {
            requestedAck = true;
            controller.notify(CommunicationMessage.SUCCESS);
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
        }
    }

    private void chooseStudentFromEntrance() {
        requestedEntranceStudent = true;
        controller.notify(new MovableStudentsChar(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents()));
    }

    private void swapStudents() {
        try {
            game.getCurrentPlayer().getSchoolBoard().getEntrance().extractStudent(entranceStudent);
            char7.removeStudent(charStudent);
            char7.addStudent(entranceStudent);
            game.getCurrentPlayer().getSchoolBoard().getEntrance().addStudent(charStudent);
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
            i++;
        } catch (StudentNotExistingException e) {
            controller.notify(ErrorMessage.INVALID_INPUT);
        }
        chooseStudentFromChar();
    }
}
