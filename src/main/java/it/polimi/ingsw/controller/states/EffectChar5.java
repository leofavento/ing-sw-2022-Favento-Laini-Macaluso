package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.messages.fromServer.WhereToMove;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.characters.Char5;

public class EffectChar5 implements State {

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
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        requestedIsland = true;
        controller.notify(new WhereToMove());
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenDestination && requestedIsland) {
            try {
                island = game.getDashboard().getIslands().get(((ChosenDestination) message).getDestination() - 1);
                requestedIsland = false;
                island.addNoEntry();
                requestedAck = true;
                controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
            } catch (IndexOutOfBoundsException e) {
                controller.notify(ErrorMessage.INVALID_INPUT);
            }
        }
        if (message instanceof Ack && requestedAck) {
            requestedAck = false;
            controller.notify(CommunicationMessage.SUCCESS);
            nextState();
        }
    }


}

