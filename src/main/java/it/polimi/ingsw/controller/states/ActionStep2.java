package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenSteps;
import it.polimi.ingsw.messages.fromClient.PlayAssistant;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionStep2 implements ResumableState {
    Game game;
    Controller controller;
    boolean requestedSteps=false;
    boolean requestedAck = false;
    boolean finished = false;
    int steps = 0;
    ArrayList<String> missingAcks = new ArrayList<>();

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
        notifyStatus(PlayerStatus.MOVE_2);
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        } else if (message instanceof ChosenSteps && requestedSteps) {
            receiveSteps((ChosenSteps) message);
        }
    }

    private void receiveSteps(ChosenSteps message) {
        if (message.getSteps() <= (game.getCurrentPlayer().getPlayedAssistant().getMovements() + game.getDashboard().getAdditionalMNMovements())
                && (message.getSteps() >= 1)){
            requestedSteps = false;
            steps = message.getSteps();
            moveMotherNature();
        } else{
            controller.notify(ErrorMessage.INVALID_INPUT);
        }
    }

    private void moveMotherNature() {
        game.getDashboard().moveMotherNature(steps);
        game.getDashboard().setAdditionalMNMovements(0);
        Island motherNatureIsland = game.getDashboard().getIslands().get(game.getDashboard().getMotherNaturePosition());

        if (motherNatureIsland.getNoEntry() > 0) {
            motherNatureIsland.useNoEntry();
            controller.notify(CommunicationMessage.NO_ENTRY_TILE_ON_ISLAND);
            //add back the No Entry Tile on the character
            //only in Char5 this method has effect, in every other is only void
            for (CharacterCard characterCard : game.getDashboard().getCharacters()) {
                characterCard.addNoEntryTile();
            }
        }
        //Island is free of No Entry Tiles
        else {
            goToResolveIsland(motherNatureIsland);
        }
    }

    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            requestedAck = false;
            checkSteps();
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

    private void goToResolveIsland(Island island) {
        controller.setState(new ResolveIsland(game, controller, island, this));
        controller.getState().execute();
    }

    private void checkSteps() {
        if (steps == 0) {
            requestedSteps = true;
            controller.notify(new MotherNatureSteps(game.getCurrentPlayer().getPlayedAssistant().getMovements()+game.getDashboard().getAdditionalMNMovements()));
        } else {
            if (finished) {
                nextState();
            } else {
                controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
                notifyStatus(PlayerStatus.END_MOVE_2);
                finished = true;
            }
        }
    }

    @Override
    public void resume() {
        checkSteps();
    }
}
