package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Char10;

import java.util.ArrayList;

public class EffectChar10 implements State {

    Game game;
    Controller controller;
    ResumableState previousState;
    Char10 char10;
    boolean requestedAck = false;
    boolean requestedDiningRoomStudent = false;
    boolean requestedEntranceStudent = false;
    int i = 0;
    Color DiningRoomStudent, EntranceStudent;

    public EffectChar10(Game game, Controller controller, ResumableState previousState, Char10 char10) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char10 = char10;
    }


    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        chooseStudentFromEntrance();
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenStudent && requestedEntranceStudent) {
            if (((ChosenStudent) message).getStudent() != null) {
                requestedEntranceStudent = false;
                this.EntranceStudent = ((ChosenStudent) message).getStudent();
                chooseStudentFromDiningRoom();
            } else {
                i = 2;
                requestedEntranceStudent = false;
                chooseStudentFromEntrance();
            }
        } else if (message instanceof ChosenStudent && requestedDiningRoomStudent) {
            this.DiningRoomStudent = ((ChosenStudent) message).getStudent();
            requestedDiningRoomStudent = false;
            swapStudents();
        } else if (message instanceof Ack && requestedAck) {
            requestedAck = false;
            nextState();
        }

    }

    private void chooseStudentFromEntrance() {
        if (i < 2) {
            requestedEntranceStudent = true;
            controller.notify(new MovableStudentsChar(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents()));
        } else {
            requestedAck = true;
            controller.notify(CommunicationMessage.SUCCESS);
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
        }
    }

    private void chooseStudentFromDiningRoom() {
        requestedDiningRoomStudent = true;
        ArrayList<Color> diningStudentsList = new ArrayList<>();
        for (Color color : Color.values()) {
            for (int z = 0; z < game.getCurrentPlayer().getSchoolBoard().getDiningRoom().getStudentsNumber(color); z++) {
                diningStudentsList.add(color);
            }
        }
        controller.notify(new MovableStudentsChar(diningStudentsList));
    }

    private void swapStudents() {
        if (!game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().contains(EntranceStudent)
                || game.getCurrentPlayer().getSchoolBoard().getDiningRoom().getStudentsNumber(DiningRoomStudent) == 0) {
            controller.notify(ErrorMessage.INVALID_INPUT);
        } else {

            try {
                game.getCurrentPlayer().getSchoolBoard().getEntrance().extractStudent(EntranceStudent);
                game.getCurrentPlayer().getSchoolBoard().getDiningRoom().extractStudent(DiningRoomStudent);
                game.getCurrentPlayer().getSchoolBoard().getEntrance().addStudent(DiningRoomStudent);
                game.getCurrentPlayer().getSchoolBoard().getDiningRoom().addStudent(EntranceStudent);
                controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
                i++;
            } catch (StudentNotExistingException e) {
                controller.notify(ErrorMessage.INVALID_INPUT);
            }
        }
        chooseStudentFromEntrance();
    }
}
