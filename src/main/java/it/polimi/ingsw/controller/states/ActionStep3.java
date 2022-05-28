package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenCloud;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ActionStep3 implements ResumableState {
    Game game;
    Controller controller;
    boolean requestedAck = false;
    boolean requestedCloud = false;
    boolean movedStudents = false;
    boolean finished = false;
    ArrayList<String> missingAcks = new ArrayList<>();

    public ActionStep3(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void nextState() {
        cleanAll();

        if (game.getFinalRound() && (game.getCurrentPlayer().equals(game.getOnlinePlayers().get(game.getNumberOfPlayers()-1)))){
            controller.check();
        }
        else{
            if (game.getCurrentPlayer().equals(game.getOnlinePlayers().get(game.getNumberOfPlayers()-1))){
                game.setNextPlayer();
                controller.setState(new Planning(game, controller));
                controller.getState().execute();}
            else{
                game.setNextPlayer();
                controller.setState(new ActionStep1(game, controller));
                controller.getState().execute();}
        }

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
        int choice = message.getCloud();
        try {
            Cloud chosenCloud = game.getDashboard().getClouds().get(choice);
            if (chosenCloud.getStudents().isEmpty()) {
                controller.notify(ErrorMessage.EMPTY_CLOUD);
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
                controller.notify(new EndOfPlayerRound(game.getRoundNumber(), game.getCurrentPlayer().getNickname()));
            }
        } catch (IndexOutOfBoundsException e) {
            controller.notify(ErrorMessage.INVALID_INPUT);
        }
    }

    private void checkClouds() {
        if (! movedStudents) {
            ArrayList<Cloud> availableClouds = new ArrayList<>(game.getDashboard().getClouds());
            availableClouds.removeIf(c -> c.getStudents().isEmpty());
            if (availableClouds.isEmpty()) { // happens only in the last turn if there aren't enough students
                nextState();
            } else {
                requestedCloud = true;
                controller.notify(new SelectCloud());
            }
        } else {
            checkLastPlayer();
        }
    }

    private void checkLastPlayer() {
        if (game.getOnlinePlayers().indexOf(game.getCurrentPlayer()) == (game.getNumberOfPlayers() - 1)) {
            controller.notify(new EndOfRound(game.getRoundNumber()));
        }
        notifyStatus(PlayerStatus.WAITING);
        finished = true;
    }

    public void cleanAll(){
        if (game.getExpertGame()){
        //Reset character6 effect
        game.getDashboard().setDoNotCountTowers(false);

        //Reset character 8 effect
        for (Island island : game.getDashboard().getIslands()) {
            island.resetExtraInfluences();
        }

        //Put back in the right place the Professor moved with the character 2
        for (CharacterCard character: game.getDashboard().getCharacters()){
            character.setInactive();
            character.resetUsedBy();
        }
        game.getDashboard().setPlayedCharacter(null);

        for (Player player: game.getOnlinePlayers()) {
            for (Professor professor:player.getSchoolBoard().getProfessors()) {
                if (!(professor.getOwner()==player)) {
                    professor.getOwner().getSchoolBoard().addProfessor(professor);
                    player.getSchoolBoard().removeProfessor(professor);
                }
            }
        }
        //Reset character9 effect
        game.getDashboard().resetDoNotCountColor();}
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
        checkClouds();
    }
}
