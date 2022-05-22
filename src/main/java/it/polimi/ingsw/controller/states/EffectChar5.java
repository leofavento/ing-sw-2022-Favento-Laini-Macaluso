package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.messages.fromServer.WhereToMove;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.characters.Char5;

public class EffectChar5 implements ResumableState {

    Game game;
    Controller controller;
    ResumableState previousState;
    Char5 char5;
    boolean requestedAck = false;
    boolean requestedIsland = false;
    Island island;

    public EffectChar5(Game game, Controller controller, ResumableState previousState, Char5 char5) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char5 = char5;
    }

    @Override
    public void resume() {

    }

    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        requestedIsland = true;
        controller.notify(new WhereToMove(null, game.getDashboard().getIslands()));
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenDestination && requestedIsland) {
            if (((ChosenDestination) message).getDestination() instanceof Island) {
                requestedIsland = false;
                island = (Island) ((ChosenDestination) message).getDestination();
                island.addNoEntry();
                requestedAck = true;
                controller.notify(new UpdateBoard(game.getDashboard().getPlayedCharacters(), game.getDashboard(), game.getOnlinePlayers()));
            } else {
                controller.notify(ErrorMessage.INVALID_INPUT);
            }
        }
        if (message instanceof Ack && requestedAck) {
            requestedAck = false;
            nextState();
        }
    }


}
