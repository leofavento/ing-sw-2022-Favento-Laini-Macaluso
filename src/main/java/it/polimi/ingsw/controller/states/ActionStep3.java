package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenCloud;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionStep3 implements State {
    Game game;
    Controller controller;
    boolean requestedAck = false;
    boolean requestedCloud = false;
    boolean movedStudents = false;
    boolean finished = false;
    ArrayList<String> missingAcks = new ArrayList<>();

    private final List<Observer<Message>> observers = new ArrayList<>();

    public ActionStep3(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void nextState() {
        if (game.getFinalRound() && (game.getCurrentPlayer().equals(game.getOnlinePlayers().get(game.getNumberOfPlayers()-1)))){
            controller.getEndGameController().check();
        }
        controller.setState(new Planning(game, controller));
        controller.getState().execute();
    }

    @Override
    public void execute() {
        notifyStatus(PlayerStatus.MOVE_3);
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        } else if (message instanceof ChosenCloud && requestedCloud) {
            receiveCloud((ChosenCloud) message);
        }
    }

    private void receiveCloud(ChosenCloud message) {
        Cloud chosenCloud = message.getCloud();
        if (chosenCloud.getStudents().isEmpty()) {
            notify(ErrorMessage.INVALID_INPUT);
        } else {
            requestedCloud = false;
            for (Color student : chosenCloud.getStudents()) {
                game.getCurrentPlayer().getSchoolBoard().getEntrance().addStudent(student);
            }
            chosenCloud.getStudents().clear();
            movedStudents = true;
            requestedAck = true;
            missingAcks.addAll(game.getOnlinePlayers().stream()
                    .map(Player::getNickname)
                    .collect(Collectors.toList()));
            notify(new EndOfPlayerRound(game.getRoundNumber(), game.getCurrentPlayer().getNickname()));
        }
    }

    private void checkClouds() {
        if (! movedStudents) {
            ArrayList<Cloud> availableClouds = new ArrayList<>(game.getDashboard().getClouds());
            availableClouds.removeIf(c -> c.getStudents().isEmpty());
            requestedCloud = true;
            notify(new SelectCloud(availableClouds));
        } else {
            checkLastPlayer();
        }
    }

    private void checkLastPlayer() {
        if (game.getOnlinePlayers().indexOf(game.getCurrentPlayer()) == (game.getNumberOfPlayers() - 1)) {
            notify(new EndOfRound(game.getRoundNumber()));
        }
        notifyStatus(PlayerStatus.END_MOVE_3);
        finished = true;
    }

    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            requestedAck = false;
            if (finished) {
                nextState();
            } else {
                checkClouds();
            }
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
