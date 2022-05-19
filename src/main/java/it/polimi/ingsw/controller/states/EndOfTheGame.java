package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.EndOfGameReason;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.CommunicateWinner;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class EndOfTheGame implements State {
    Game game;
    Controller controller;
    EndOfGameReason reason;
    ArrayList<String> winnerNicknames;

    public EndOfTheGame(Game game, Controller controller, EndOfGameReason reason) {
        this.game = game;
        this.controller = controller;
        this.reason = reason;
    }
    
    @Override
    public void nextState() {
    }

    @Override
    public void execute() {
        winnerNicknames.add(game.getWinners().get(0).getNickname());
        winnerNicknames.add(game.getWinners().get(1).getNickname());

        controller.notify(new CommunicateWinner(winnerNicknames, this.reason));

        //TODO close connection with all the clients
    }

    @Override
    public void receiveMessage(Message message, String sender) {

    }

    @Override
    public void addObserver(Observer<Message> observer) {

    }

    @Override
    public void notify(Message message) {

    }
}
