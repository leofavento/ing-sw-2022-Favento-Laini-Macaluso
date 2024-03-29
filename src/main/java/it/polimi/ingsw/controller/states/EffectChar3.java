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
import it.polimi.ingsw.model.characters.Char3;

/**
 * This state implements the effect of Character 3.
 * The player that activate this character will take control of any number of professors, even if the player
 * has the same number of students as the player who controls them.
 */
public class EffectChar3 implements ResumableState {

    Game game;
    Controller controller;
    ResumableState previousState;
    Char3 char3;
    boolean requestedAck = false;
    boolean requestedIsland = false;
    Island island;

    public EffectChar3(Game game, Controller controller, ResumableState previousState, Char3 char3) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char3 = char3;
    }


    @Override
    public void resume() {
        requestedAck=true;
        controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
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
                island = game.getDashboard().getIslands().get((((ChosenDestination) message).getDestination() - 1));
                requestedIsland = false;
                ResumableState thisState = this;
                controller.setState(new ResolveIsland(game, controller, island, thisState));
                controller.getState().execute();
            } catch (IndexOutOfBoundsException e) {
                controller.notify(ErrorMessage.INVALID_INPUT);
            }
        }
        else if (message instanceof Ack && requestedAck){
            requestedAck=false;
            controller.notify(CommunicationMessage.SUCCESS);
            nextState();
        }
    }
}
