package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * character 3 state
 */
public class Char3State {

    /**
     * method used to print to command line the effects of character 3
     * @param cli the game's command line interface
     */
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

            System.out.println("Select the island you want to resolve:");
            try {
                in.reset();
                selection = in.nextInt();
                cli.getClient().sendMessage(new ChosenDestination(selection));
            } catch (InputMismatchException e) {
                in.next();
                System.out.println("Please enter an integer.");
                continue;
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
