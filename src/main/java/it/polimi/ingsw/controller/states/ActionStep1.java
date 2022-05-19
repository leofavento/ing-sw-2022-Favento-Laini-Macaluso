package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Action;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.FullDiningRoomException;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.StudentDeposit;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionStep1 implements State {
    Game game;
    Controller controller;
    boolean requestedAck = false;
    boolean requestedStudent = false;
    boolean requestedDestination = false;
    Map<Color, StudentDeposit> moveStudent = new HashMap<>();
    ArrayList<String> missingAcks = new ArrayList<>();
    int movedStudents = 0;
    Color currStudent;

    private final List<Observer<Message>> observers = new ArrayList<>();

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
        notify(new StartOfPlayerRound(game.getRoundNumber(), game.getCurrentPlayer().getNickname()));
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
            notify(new MovableStudents(game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents()));
        } else {
            nextState();
        }
    }

    private void receiveStudent(ChosenStudent message) {
        Color student = message.getStudent();
        if (! game.getCurrentPlayer().getSchoolBoard().getEntrance().getStudents().contains(student)) {
            notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
        } else {
            requestedStudent = false;
            currStudent = student;
            requestedDestination = true;
            notify(new WhereToMove(true, game.getDashboard().getIslands().size()));
        }
    }

    private void receiveDestination(ChosenDestination message) {
        try {
            if (message.getDestination() instanceof DiningRoom) {
                Action.moveFromEntranceToDining(currStudent, game.getCurrentPlayer().getSchoolBoard());
                game.updateProfessors();
            } else if (message.getDestination() instanceof Island) {
                Action.moveFromEntranceToIsland(currStudent,
                        game.getCurrentPlayer().getSchoolBoard().getEntrance(),
                        (Island) message.getDestination());
            }
            notify(CommunicationMessage.SUCCESS); // ha senso?
            movedStudents++;
            missingAcks.addAll(game.getOnlinePlayers().stream()
                    .map(Player::getNickname)
                    .collect(Collectors.toList()));
            requestedAck = true;
            notify(new UpdateBoard(null, game.getDashboard(), game.getOnlinePlayers()));
            if (movedStudents == ((game.getNumberOfPlayers() == 3) ? 4 : 3)) {
                notifyEndMove();
            }
        } catch (FullDiningRoomException e) {
            notify(ErrorMessage.FULL_DINING_ROOM);
        } catch (StudentNotExistingException e) {
            notify(ErrorMessage.STUDENT_NOT_AVAILABLE);
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
        setStatus(PlayerStatus.END_MOVE_1);
        notify(new PlayerStatusMessage(game.getCurrentPlayer().getStatus()));
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
