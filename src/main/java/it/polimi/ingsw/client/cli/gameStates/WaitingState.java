package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.model.player.Player;

public class WaitingState implements State {
    private final CLI cli;

    public WaitingState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        if (cli.getView().getRoundNumber() == 0) {
            System.out.println("The order for the first round was extracted randomly:");
            for (Player player : cli.getView().getPlayers()) {
                System.out.printf("%d) %s%n",
                        cli.getView().getPlayers().indexOf(player) + 1,
                        player.getNickname());
                }
            System.out.println("Wait until the other players make their move...");
        }
    }
}
