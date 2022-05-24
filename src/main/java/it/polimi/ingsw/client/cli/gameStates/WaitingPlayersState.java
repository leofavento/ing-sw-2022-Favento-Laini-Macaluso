package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;

public class WaitingPlayersState implements State {
    private final CLI cli;

    public WaitingPlayersState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        while (!cli.isSuccess()) {
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

        if (cli.isSuccess()) {
            cli.setSuccess(false);
        }
    }
}
