package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.PlayersOrderRenderer;
import it.polimi.ingsw.messages.fromClient.Ack;

public class WaitingState implements State {
    private final CLI cli;

    public WaitingState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        cli.getClient().sendMessage(new Ack());
        if (cli.getView().getRoundNumber() == 0) {
            System.out.println("The order for the first round was extracted randomly:");
            PlayersOrderRenderer.playersOrderRenderer(cli.getView().getPlayers());
            System.out.println("Wait until the other players make their move...");
        } else {
            System.out.println("Please wait...");
        }
    }
}
