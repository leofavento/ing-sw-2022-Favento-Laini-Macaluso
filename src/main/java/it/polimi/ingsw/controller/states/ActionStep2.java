package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenSteps;
import it.polimi.ingsw.messages.fromClient.PlayAssistant;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.MotherNatureSteps;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ActionStep2 implements State{
    Game game;
    Controller controller;
    boolean requestedSteps=false;
    boolean requestedAck = false;

    private final List<Observer<Message>> observers = new ArrayList<>();

    public ActionStep2(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }


    @Override
    public void nextState() {
        //controller.setState(new ActionStep3(game, controller));
        //controller.getState().execute();

    }

    @Override
    public void execute() {
        requestedSteps=true;
        notify(new MotherNatureSteps(game.getCurrentPlayer().getPlayedAssistant().getMovements()+game.getDashboard().getAdditionalMNMovements()));
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if ((! sender.equals(game.getCurrentPlayer().getNickname())) && ! requestedAck) {
            notify(ErrorMessage.WRONG_TURN);
        } else if (message instanceof ChosenSteps && requestedSteps) {
            receiveSteps((ChosenSteps) message);
        } else if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        }

    }

    private void receiveSteps(ChosenSteps message) {
        if (message.getSteps()<=(game.getCurrentPlayer().getPlayedAssistant().getMovements()+game.getDashboard().getAdditionalMNMovements())&&(message.getSteps()>=1)){
        controller.motherNatureMovement(message.getSteps());
        requestedAck=true;}
        else{
            notify(ErrorMessage.INVALID_INPUT);
        }
    }


    private void receiveAck(String sender) {
        if (sender.equals(game.getCurrentPlayer().getNickname())) {
            requestedAck = false;
        }
    }
    @Override
    public void addObserver(Observer<Message> observer) {

    }

    @Override
    public void notify(Message message) {

    }
}
