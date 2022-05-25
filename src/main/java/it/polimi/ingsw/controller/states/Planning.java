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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Planning implements ResumableState {
    Game game;
    Controller controller;
    boolean requestedAck = false;
    boolean requestedAssistant = false;
    Map<Player, Assistant> playedAssistants = new HashMap<>();
    ArrayList<String> missingAcks = new ArrayList<>();

    public Planning(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void nextState() {
        if (! game.getFinalRound()) {
            controller.isFinalRound();
        }
        controller.setState(new ActionStep1(game, controller));
        controller.getState().execute();
    }

    @Override
    public void execute() {
        game.newRound();
        int studentsPerCloud = (game.getNumberOfPlayers() == 3) ? 4 : 3;
        int necessaryStudents = studentsPerCloud * game.getDashboard().getClouds().size();
        if (game.getDashboard().getBag().getStudentsLeft() <= necessaryStudents
                || game.getCurrentPlayer().getAvailableAssistants().size() == 1) {
            game.setFinalRound();
            controller.notify(CommunicationMessage.LAST_ROUND);
        }
        if (game.getDashboard().getBag().getStudentsLeft() >= necessaryStudents) {
            initializeStudentsToClouds(studentsPerCloud);
        }
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
                controller.notify(new PlayedAssistant(assistant, game.getCurrentPlayer().getNickname()));
                game.setNextPlayer();
                if (! playedAssistants.containsKey(game.getCurrentPlayer())) {
                    notifyStatus(PlayerStatus.PLANNING);
                } else {
                    controller.updateTurnOrder();
                    controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
                    notifyStatus(PlayerStatus.WAITING);
                }
            } else {
                controller.notify(ErrorMessage.INVALID_ASSISTANT);
            }
        } catch (AlreadyPlayedAssistant e) {
            controller.notify(ErrorMessage.UNAVAILABLE_ASSISTANT);
        }
    }

    private void checkAssistants() {
        if (! playedAssistants.containsKey(game.getCurrentPlayer())) {
            requestedAssistant = true;
            controller.notify(new AvailableAssistants(game.getCurrentPlayer().getAvailableAssistants(), playedAssistants));
        } else {
            nextState();
        }
    }

    private void initializeStudentsToClouds(int numOfStudents) {
        for (Cloud cloud : game.getDashboard().getClouds()) {
            Action.moveFromBagToDeposit(game.getDashboard().getBag(), cloud, numOfStudents);
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
        missingAcks.clear();
        missingAcks.addAll(game.getOnlinePlayers().stream()
                .map(Player::getNickname)
                .collect(Collectors.toList()));
        setStatus(currPlayerStatus);
        controller.notify(new PlayerStatusMessage(game.getCurrentPlayer().getStatus()));
    }

    private void setStatus(PlayerStatus currPlayerStatus) {
        for (Player player : game.getOnlinePlayers()) {
            player.setStatus(PlayerStatus.WAITING);
        }
        game.getCurrentPlayer().setStatus(currPlayerStatus);
    }

    @Override
    public void resume() {
        checkAssistants();
    }
}
