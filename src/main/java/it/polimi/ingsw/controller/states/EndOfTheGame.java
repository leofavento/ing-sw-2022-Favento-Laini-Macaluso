package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.EndOfGameReason;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.CommunicateWinner;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
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
        Tower winnerTeam;
        try {
            winnerTeam = game.getWinners().get(0).getSchoolBoard().getTowerColor();
            System.out.printf("End of game %d. %s team won, congratulations ",
                    game.getRoundNumber(),
                    winnerTeam);
            for (String winner : winnerNicknames) {
                System.out.printf("%s%s",
                        winner,
                        winnerNicknames.indexOf(winner) == winnerNicknames.size() - 1 ? "!" : ", ");
            }
            System.out.println();
        } catch (IndexOutOfBoundsException e) {
            winnerTeam = null;
            System.out.println("End of game %d. It's a tie, congratulations to everyone that played!");
        }
        controller.notify(new CommunicateWinner(winnerTeam, winnerNicknames, reason));
    }

    @Override
    public void receiveMessage(Message message, String sender) {

    }
}
