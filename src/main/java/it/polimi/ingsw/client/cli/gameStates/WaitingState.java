package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.CloudsRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.PlayersOrderRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.SchoolBoardRenderer;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.player.PlayerStatus;

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
        } else if (cli.getView().getCurrentStatus() != PlayerStatus.WAITING) {
            cli.getView().setCurrentStatus(PlayerStatus.WAITING);
            CloudsRenderer.cloudRenderer(cli.getView().getDashboard());
            IslandsRenderer.islandsRenderer(cli.getView().getDashboard());
            SchoolBoardRenderer.renderAllSchoolBoards(cli.getView().getPlayers(), cli.getView().isExpertMode());
            System.out.println("Players order:");
            PlayersOrderRenderer.playersOrderRenderer(cli.getView().getPlayers());
        }
    }
}
