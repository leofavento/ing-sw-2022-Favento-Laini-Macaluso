package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.MovableStudents;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Char10;

import java.util.ArrayList;

public class EffectChar10 implements ResumableState{

    Game game;
    Controller controller;
    ResumableState previousState;
    Char10 char10;
    boolean requestedAck = false;
    boolean requestedDiningRoomStudent = false;
    boolean requestedEntranceStudent = false;
    int i=0;
    Color DiningRoomStudent, EntranceStudent;

    public EffectChar10(Game game, Controller controller, ResumableState previousState, Char10 char10) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char10 = char10;
    }


    @Override
    public void resume() {
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
    public void receiveMessage(Message message, String sender){
        if (message instanceof ChosenStudent && requestedEntranceStudent){
            if (!(((ChosenStudent) message).getStudent()==null)){
                requestedEntranceStudent=false;
                this.EntranceStudent=((ChosenStudent) message).getStudent();
                chooseStudentFromDiningRoom();
            }
            else {
                i=2;
                requestedEntranceStudent=false;
                nextState();
            }
        }
        if (message instanceof ChosenStudent && requestedDiningRoomStudent){
            this.DiningRoomStudent=((ChosenStudent) message).getStudent();
            requestedDiningRoomStudent=false;
            swapStudents();
        }
        if (message instanceof Ack && requestedAck){
            requestedAck=false;
            nextState();
        }

    }

    private void chooseStudentFromEntrance(){
        requestedEntranceStudent=true;
        if (i<2){
            controller.notify(new MovableStudents(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents()));
        }
        else {
            requestedAck=true;
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
        }
    }

    private void chooseStudentFromDiningRoom(){
        requestedDiningRoomStudent=true;
        ArrayList<Color> entranceStudentsList = new ArrayList<>();
        for (Color color: Color.values()) {
            for (int i=0; i<game.getCurrentPlayer().getSchoolBoard().getDiningRoom().getStudentsNumber(color); i++) {
                entranceStudentsList.add(color);
            }
        }
        controller.notify(new MovableStudents(entranceStudentsList));
    }

    private void swapStudents(){
        try{
        game.getCurrentPlayer().getSchoolBoard().getEntrance().extractStudent(EntranceStudent);}
        catch (StudentNotExistingException e){
            controller.notify(ErrorMessage.INVALID_INPUT);
        }
        try{
        game.getCurrentPlayer().getSchoolBoard().getDiningRoom().extractStudent(DiningRoomStudent);}
        catch (StudentNotExistingException e){
            controller.notify(ErrorMessage.INVALID_INPUT);
        }
        game.getCurrentPlayer().getSchoolBoard().getEntrance().addStudent(DiningRoomStudent);
        game.getCurrentPlayer().getSchoolBoard().getDiningRoom().addStudent(EntranceStudent);

        i++;
        chooseStudentFromEntrance();
    }
}
