package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.StateManager;
import it.polimi.ingsw.client.cli.utilities.PrintableStrings;
import it.polimi.ingsw.messages.fromClient.JoinAvailableGame;
import it.polimi.ingsw.messages.fromClient.RequestGames;
import it.polimi.ingsw.model.GameInfo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JoinGameState implements State {
    private final CLI cli;

    public JoinGameState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        int choice = 0;

        while (choice == 0 || !cli.isSuccess()) {
            System.out.print(PrintableStrings.joinGameOptions);
            cli.getClient().sendMessage(new RequestGames());
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            printAvailableGames();
            System.out.println("Enter your choice: ");
            try {
                in.reset();
                choice = in.nextInt();

                if (choice < -1) {
                    System.out.println("This option is not valid.");
                } else if (choice == -1) {
                    new Thread(new StateManager(cli, new LobbyState(cli))).start();
                    break;
                } else if (choice > 0) {
                    cli.getClient().sendMessage(new JoinAvailableGame(choice));
                    try {
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (cli.getView().getLastErrorMessage() != null) {
                        System.out.println(cli.getView().getLastErrorMessage().getMessage());
                        cli.getView().setLastErrorMessage(null);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("You have to enter an integer.");
                choice = 0;
                in.next();
            }
        }

        if (cli.isSuccess()) {
            cli.setSuccess(false);
            new Thread(new StateManager(cli, new WaitingPlayersState(cli))).start();
        }
    }

    private void printAvailableGames() {
        ArrayList<GameInfo> availableGames = cli.getAvailableGames();

        System.out.println("+------GameID-----Players-----Expert Mode----+");
        for (GameInfo game : availableGames) {
            String s = String.format("|        %d           %d/%d           %c         |",
                    game.getGameID(),
                    game.getNumOfWaitingPlayers(),
                    game.getNumOfTotalPlayers(),
                    (game.isExpertGame() ? 'Y' : 'N'));
            System.out.println(s);
        }
        if (availableGames.size() == 0) {
            System.out.println("|                                            |");
        }
        System.out.println("+--------------------------------------------+");
    }
}
