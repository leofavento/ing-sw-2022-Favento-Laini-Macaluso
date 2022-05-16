package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Action;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistant;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.PlayAssistant;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Planning implements State {
    Game game;
    Controller controller;
    boolean requestedAck = false;
    boolean requestedAssistant = false;
    Map<Player, Assistant> playedAssistants = new HashMap<>();
    ArrayList<String> missingAcks = new ArrayList<>();

    private final List<Observer<Message>> observers = new ArrayList<>();

    public Planning(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void nextState() {
        controller.setState(new ActionStep1(game, controller));
        controller.getState().execute();
    }

    @Override
    public void execute() {
        game.newRound();
        initializeStudentsToClouds();
        notifyStatus(PlayerStatus.PLANNING);
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof PlayAssistant && requestedAssistant) {
            receiveAssistant((PlayAssistant) message);
        } else if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        }
    }

    private void receiveAssistant(PlayAssistant message) {
        Assistant assistant = message.getAssistant();

        try {
            if (! playedAssistants.containsValue(assistant)
                    || playedAssistants.values().containsAll(game.getCurrentPlayer().getAvailableAssistants())) {
                game.getCurrentPlayer().playAssistant(assistant);
                requestedAssistant = false;
                playedAssistants.put(game.getCurrentPlayer(), assistant);
                notify(new PlayedAssistant(assistant, game.getCurrentPlayer().getNickname()));
                game.setNextPlayer();
                if (! playedAssistants.containsKey(game.getCurrentPlayer())) {
                    notifyStatus(PlayerStatus.PLANNING);
                } else {
                    controller.updateTurnOrder();
                    notify(new UpdateBoard(null, null, game.getOnlinePlayers()));
                    notifyStatus(PlayerStatus.WAITING);
                }
            } else {
                notify(ErrorMessage.INVALID_ASSISTANT);
            }
        } catch (AlreadyPlayedAssistant e) {
            notify(ErrorMessage.UNAVAILABLE_ASSISTANT);
        }
    }

    private void checkAssistants() {
        if (! playedAssistants.containsKey(game.getCurrentPlayer())) {
            requestedAssistant = true;
            notify(new AvailableAssistants(game.getCurrentPlayer().getAvailableAssistants(), playedAssistants));
        } else {
            nextState();
        }
    }

    private void initializeStudentsToClouds() {
        for (Cloud cloud : game.getDashboard().getClouds()) {
            switch (game.getNumberOfPlayers()) {
                case (2):
                case (4):
                    Action.moveFromBagToDeposit(game.getDashboard().getBag(),
                            cloud,
                            3);
                    break;
                case (3):
                    Action.moveFromBagToDeposit(game.getDashboard().getBag(),
                            cloud,
                            4);
                    break;
            }
        }
    }

    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            requestedAck = false;
            checkAssistants();
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
