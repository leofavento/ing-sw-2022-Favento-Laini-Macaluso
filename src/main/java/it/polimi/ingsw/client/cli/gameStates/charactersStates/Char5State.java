package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Char5State {
    public void run(CLI cli) {
        int selection;
        Scanner in = new Scanner(System.in);

        while (!cli.isSuccess()) {
            if (!cli.getView().getRequiredDestination()) {
                try {
                    synchronized (cli.getGameState()) {
                        cli.getGameState().wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            IslandsRenderer.islandsRenderer(cli.getView().getDashboard());

            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            System.out.println("Select the island where you wish to place a No Entry tile:");
            try {
                in.reset();
                selection = in.nextInt();
                cli.getClient().sendMessage(new ChosenDestination(selection));
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer.");
            }
            if (!cli.isSuccess()) {
                try {
                    synchronized (cli.getGameState()) {
                        cli.getGameState().wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (cli.isSuccess()) {
            cli.setSuccess(false);
            cli.getView().setRequiredDestination(false);
        }
    }
}
