package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Action;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
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

/**
 * Planning is the first state of every turn.
 * In this state the Clouds are filled with Students from the Bag and the Player chooses
 * an Assistant between the available ones in his deck.
 */
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

    /**
     * method used to change state.
     * First the method checks if this is the final round.
     * Then the state is changed to ActionStep1.
     */
    @Override
    public void nextState() {
        if (! game.getFinalRound()) {
            controller.isFinalRound();
        }
        controller.setState(new ActionStep1(game, controller));
        controller.getState().execute();
    }

    /**
     * method used to refill the Clouds.
     * First the method increments the round counter, then it checks if there are enough students to
     * fill the Clouds with the right number of Students, based on the Players number.
     * If yes, it proceeds to fill them.
     * If no, the Server communicates to all Players this will be the last round.
     */
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

    /**
     * method used to distinguish the received message and to trigger the right action.
     * @param message the message received
     * @param sender the sender nickname
     */
    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof PlayAssistant && requestedAssistant) {
            receiveAssistant((PlayAssistant) message);
        } else if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        }
    }

    /**
     * method used to set the Assistant selected by the Player.
     * If the Assistant is already used by another Player in this turn, or the Player has already
     * selected an Assistant before, the server communicates the error to the Player.
     * Otherwise, the server communicates the selected Assistant and the name of the Player to all
     * the other Players.
     * Then this method updates the turn order and sends the updated board to all players.
     *
     * @param message the message containing the chosen Assistant
     */
    private void receiveAssistant(PlayAssistant message) {
        Assistant assistant = message.getAssistant();

        try {
            if (! playedAssistants.containsValue(assistant)
                    || playedAssistants.values().containsAll(game.getCurrentPlayer().getAvailableAssistants())) {
                game.getCurrentPlayer().playAssistant(assistant);
                requestedAssistant = false;
                playedAssistants.put(game.getCurrentPlayer(), assistant);
                controller.notify(new PlayedAssistant(assistant, game.getCurrentPlayer().getNickname()));
                notifyStatus(PlayerStatus.END_PLANNING);
                game.setNextPlayer();
                if (! playedAssistants.containsKey(game.getCurrentPlayer())) {
                    notifyStatus(PlayerStatus.PLANNING);
                } else {
                    controller.updateTurnOrder();
                    game.setCurrentPlayer(game.getOnlinePlayers().get(0));
                    controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
                }
            } else {
                controller.notify(ErrorMessage.INVALID_ASSISTANT);
            }
        } catch (AlreadyPlayedAssistantException e) {
            controller.notify(ErrorMessage.UNAVAILABLE_ASSISTANT);
        }
    }

    /**
     * method used to check if the Player has already selected an Assistant.
     * If not, it sends the list of available Assistants and the list of Assistant selected
     * by other Players
     */
    private void checkAssistants() {
        if (! playedAssistants.containsKey(game.getCurrentPlayer())) {
            requestedAssistant = true;
            controller.notify(new AvailableAssistants(game.getCurrentPlayer().getAvailableAssistants(), playedAssistants));
        } else {
            nextState();
        }
    }

    /**
     * method used to fill every Cloud with the right number of Students.
     * @param numOfStudents the right number of Students based on the Players in the Game
     */
    private void initializeStudentsToClouds(int numOfStudents) {
        for (Cloud cloud : game.getDashboard().getClouds()) {
            Action.moveFromBagToDeposit(game.getDashboard().getBag(), cloud, numOfStudents);
        }
    }

    /**
     * method used to remove the sender from the list of missing acknowledgment messages.
     * @param sender the sender nickname
     */
    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            requestedAck = false;
            checkAssistants();
        }
    }

    /**
     * method used to notify the Player about his status changed with the provided one.
     * @param currPlayerStatus the provided status to set
     */
    private void notifyStatus(PlayerStatus currPlayerStatus) {
        requestedAck = true;
        missingAcks.clear();
        missingAcks.addAll(game.getOnlinePlayers().stream()
                .map(Player::getNickname)
                .collect(Collectors.toList()));
        setStatus(currPlayerStatus);
        controller.notify(new PlayerStatusMessage(game.getCurrentPlayer().getStatus()));
    }

    /**
     * method used to change the player status with the provided.
     * All the other Players are set to the waiting status.
     * @param currPlayerStatus the provided status to set
     */
    private void setStatus(PlayerStatus currPlayerStatus) {
        for (Player player : game.getOnlinePlayers()) {
            player.setStatus(PlayerStatus.WAITING);
        }
        game.getCurrentPlayer().setStatus(currPlayerStatus);
    }

    /**
     * method used to resume the status of the Planning state.
     */
    @Override
    public void resume() {
        checkAssistants();
    }
}
