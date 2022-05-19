package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenSteps;
import it.polimi.ingsw.messages.fromClient.PlayAssistant;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.MotherNatureSteps;
import it.polimi.ingsw.messages.fromServer.PlayerStatusMessage;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionStep2 implements State{
    Game game;
    Controller controller;
    boolean requestedSteps=false;
    boolean requestedAck = false;
    ArrayList<String> missingAcks = new ArrayList<>();

    private final List<Observer<Message>> observers = new ArrayList<>();

    public ActionStep2(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }


    @Override
    public void nextState() {
        controller.setState(new ActionStep3(game, controller));
        controller.getState().execute();

    }

    @Override
    public void execute() {
        setStatus(PlayerStatus.MOVE_2);
        notifyStatus(PlayerStatus.MOVE_2);
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
        requestedAck=true;
        notifyEndMove();
        }
        else{
            notify(ErrorMessage.INVALID_INPUT);
        }
    }


    private void receiveAck(String sender) {
        if (sender.equals(game.getCurrentPlayer().getNickname())) {
            requestedAck = false;
        }
    }

    private void notifyStatus(PlayerStatus currPlayerStatus) {
        requestedAck = true;
        missingAcks.addAll(game.getOnlinePlayers().stream()
                .map(Player::getNickname)
                .collect(Collectors.toList()));
        setStatus(currPlayerStatus);
        notify(new PlayerStatusMessage(game.getCurrentPlayer().getStatus()));
    }

    private void setStatus(PlayerStatus currPlayerStatus) {
        for (Player player : game.getOnlinePlayers()) {
            player.setStatus(PlayerStatus.WAITING);
        }
        game.getCurrentPlayer().setStatus(currPlayerStatus);
    }

    private void notifyEndMove() {
        setStatus(PlayerStatus.END_MOVE_2);
        notify(new PlayerStatusMessage(game.getCurrentPlayer().getStatus()));
        nextState();
    }

    @Override
    public void addObserver(Observer<Message> observer) {
        observers.add(observer);
    }

    @Override
    public void notify(Message message) {
        for(Observer<Message> o : observers) {
            o.update(message);
        }
    }
}
