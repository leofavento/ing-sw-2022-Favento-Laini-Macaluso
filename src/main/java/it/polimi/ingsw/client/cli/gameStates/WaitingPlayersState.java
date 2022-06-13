package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;

/**
 * In the WaitingPlayersState phase, the server communicates to every player the number of
 * players currently connected and the number of required players to start the game.
 */
public class WaitingPlayersState implements State {
    private final CLI cli;

    public WaitingPlayersState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        while (cli.getView().getActivePlayers() < cli.getView().getTotalPlayers()) {
            if (cli.getView().getActivePlayers() < cli.getView().getTotalPlayers()) {
                System.out.printf("%d player%s currently connected. Waiting for %d more...%n",
                        cli.getView().getActivePlayers(),
                        cli.getView().getActivePlayers() > 1 ? "s" : "",
                        cli.getView().getTotalPlayers() - cli.getView().getActivePlayers());
            }
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
