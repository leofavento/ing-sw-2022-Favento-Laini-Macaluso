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
            System.out.println("Waiting for more players to join...");
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
