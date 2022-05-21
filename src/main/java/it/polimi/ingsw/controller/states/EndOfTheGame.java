package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.EndOfGameReason;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.CommunicateWinner;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        winnerNicknames.addAll(game.getWinners().stream().map(Player::getNickname).collect(Collectors.toList()));

        controller.notify(new CommunicateWinner(winnerNicknames, this.reason));
    }

    @Override
    public void receiveMessage(Message message, String sender) {

    }
}
