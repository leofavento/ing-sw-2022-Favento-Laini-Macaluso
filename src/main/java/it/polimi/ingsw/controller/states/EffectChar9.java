package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.messages.fromServer.SelectColor;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Char9;

/**
 * This state implements the effect of Character 9.
 * The player that activate this character chooses a color of student.
 * During the influence calculation in that turn, that color adds no influence.
 */
public class EffectChar9 implements State {

    Game game;
    Controller controller;
    ResumableState previousState;
    Char9 char9;
    boolean requestedAck = false;
    boolean requestedColor = false;
    Color color;

    public EffectChar9(Game game, Controller controller, ResumableState previousState, Char9 char9) {
        this.game = game;
        this.controller = controller;
        this.previousState = previousState;
        this.char9 = char9;
    }

    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        requestedColor = true;
        controller.notify(new SelectColor());
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenStudent && requestedColor) {
            this.color = ((ChosenStudent) message).getStudent();
            game.getDashboard().setDoNotCountColor(color);
            requestedColor = false;
            requestedAck = true;
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
            controller.notify(CommunicationMessage.SUCCESS);
        } else if (message instanceof Ack && requestedAck) {
            requestedAck = false;
            nextState();
        }
    }
}
