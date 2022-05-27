package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Action;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.FullDiningRoomException;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * ActionStep1 is the first action the Player can perform.
 * The Player can move 3 Students from his Entrance in to his DiningRoom or a selected Island.
 * He can choose every combination.
 */
public class ActionStep1 implements ResumableState {
    Game game;
    Controller controller;
    boolean requestedAck = false;
    boolean requestedStudent = false;
    boolean requestedDestination = false;
    ArrayList<String> missingAcks = new ArrayList<>();
    int movedStudents = 0;
    Color currStudent;

    /**
     * constructor method
     * @param game the current game
     * @param controller the controller
     */
    public ActionStep1(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    /**
     * method used when the Action 1 is ended, to set the Action 2 Phase
     */
    @Override
    public void nextState() {
        controller.setState(new ActionStep2(game, controller));
        controller.getState().execute();
    }

    /**
     * method used to notify the start of the current player round.
     */
    @Override
    public void execute() {
        controller.notify(new StartOfPlayerRound(game.getRoundNumber(), game.getCurrentPlayer().getNickname()));
        notifyStatus(PlayerStatus.MOVE_1);
    }

    /**
     * method used to receive a message from a player.
     * If the server requires an acknowledgment message, it calls the receiveAck method.
     * If the server asked for a student and the message contains a student, it calls the receiveStudent method.
     * If the server asked for a destination and the message contains a destination, it calls the receiveDestination method.
     * @param message the message received
     * @param sender the sender nickname
     */
    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        } else if (message instanceof ChosenStudent && requestedStudent) {
            receiveStudent((ChosenStudent) message);
        } else if (message instanceof ChosenDestination && requestedDestination) {
            receiveDestination((ChosenDestination) message);
        }
    }

    /**
     * method used to remove the ack request for a player.
     * @param sender the nickname of the sender
     */
    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            requestedAck = false;
            checkStudents();
        }
    }

    /**
     * method used to check for the students moved.
     * If the player has not moved 3 students yet, the server asks to pick one student from entrance.
     * If the player has already moved 3 students, it calls the nextState method.
     */
    private void checkStudents() {
        int movableStudents = (game.getNumberOfPlayers() == 3) ? 4 : 3;
        if (movedStudents < movableStudents) {
            requestedStudent = true;
            controller.notify(new MovableStudents(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents()));
        } else {
            nextState();
        }
    }

    /**
     * method used after the player selected the student to move.
     * The method checks if the student is contained in the active player entrance.
     * If yes, it proceeds to ask for the destination.
     * Otherwise, the server sends an error message.
     * @param message the message containing the selected player.
     */
    private void receiveStudent(ChosenStudent message) {
        Color student = message.getStudent();
        if (! game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().contains(student)) {
            controller.notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
        } else {
            requestedStudent = false;
            currStudent = student;
            requestedDestination = true;
            controller.notify(new WhereToMove());
        }
    }

    /**
     * method used after the player selected where to move the student (DiningRoom or an Island).
     * The method moves the student in the right place and increments the movement counter.
     * Then the method adds every player in the list of missing acks, and sends the updated board
     * to every player.
     * If the current player has already moved 3 students, the method communicates the end of the Action 1.
     *
     *
     * @param message the message containing the selected destination
     */
    private void receiveDestination(ChosenDestination message) {
        try {
            if (message.getDestination() == 0) {
                Action.moveFromEntranceToDining(currStudent, game.getCurrentPlayer().getSchoolBoard());
                game.updateProfessors();
            } else if (message.getDestination() != 0) {
                Action.moveFromEntranceToIsland(currStudent,
                        game.getCurrentPlayer().getSchoolBoard().getEntrance(),
                        game.getDashboard().getIslands().get(message.getDestination() - 1));
            }
            movedStudents++;
            missingAcks.addAll(game.getOnlinePlayers().stream()
                    .map(Player::getNickname)
                    .collect(Collectors.toList()));
            requestedAck = true;
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
            if (movedStudents == ((game.getNumberOfPlayers() == 3) ? 4 : 3)) {
                controller.notify(CommunicationMessage.SUCCESS);
                notifyStatus(PlayerStatus.END_MOVE_1);
            } else {
                controller.notify(CommunicationMessage.STUDENT_MOVED);
            }
        } catch (FullDiningRoomException e) {
            controller.notify(ErrorMessage.FULL_DINING_ROOM);
            requestedDestination = false;
            requestedStudent = true;
        } catch (StudentNotExistingException e) {
            controller.notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
            requestedDestination = false;
            requestedStudent = true;
        } catch (IndexOutOfBoundsException e) {
            controller.notify(ErrorMessage.INVALID_INPUT);
            requestedDestination = false;
            requestedStudent = true;
        }
    }

    /**
     * method used to add all the players in the list of missing acknowledgments.
     * It calls the setStatus method to set everyone, but the current player, to the waiting status.
     * The current player is set to Move 1 state.
     * In the end the method notifies the current player about his status.
     * @param currPlayerStatus the current status of the player
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
     * method used to set every player to the Waiting state, except for the active player.
     * The active player is set to 'currPlayerStatus' parameter.
     * @param currPlayerStatus the current player status
     */
    private void setStatus(PlayerStatus currPlayerStatus) {
        for (Player player : game.getOnlinePlayers()) {
            player.setStatus(PlayerStatus.WAITING);
        }
        game.getCurrentPlayer().setStatus(currPlayerStatus);
    }

    /**
     * method used to resume the state
     */
    @Override
    public void resume() {
        checkStudents();
    }
}


