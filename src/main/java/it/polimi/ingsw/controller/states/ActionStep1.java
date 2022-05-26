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
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ActionStep1 implements ResumableState {
    Game game;
    Controller controller;
    boolean requestedAck = false;
    boolean requestedStudent = false;
    boolean requestedDestination = false;
    ArrayList<String> missingAcks = new ArrayList<>();
    int movedStudents = 0;
    Color currStudent;

    public ActionStep1(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void nextState() {
        controller.setState(new ActionStep2(game, controller));
        controller.getState().execute();
    }

    @Override
    public void execute() {
        controller.notify(new StartOfPlayerRound(game.getRoundNumber(), game.getCurrentPlayer().getNickname()));
        notifyStatus(PlayerStatus.MOVE_1);
    }

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

    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            requestedAck = false;
            checkStudents();
        }
    }

    private void checkStudents() {
        int movableStudents = (game.getNumberOfPlayers() == 3) ? 4 : 3;
        if (movedStudents < movableStudents) {
            requestedStudent = true;
            controller.notify(new MovableStudents(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents()));
        } else {
            nextState();
        }
    }

    private void receiveStudent(ChosenStudent message) {
        Color student = message.getStudent();
        if (! game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().contains(student)) {
            controller.notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
            controller.notify(new MovableStudents(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents()));
        } else {
            requestedStudent = false;
            currStudent = student;
            requestedDestination = true;
            controller.notify(new WhereToMove());
        }
    }

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
        } catch (StudentNotExistingException e) {
            controller.notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
        } catch (IndexOutOfBoundsException e) {
            controller.notify(ErrorMessage.INVALID_INPUT);
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
        checkStudents();
    }
}
