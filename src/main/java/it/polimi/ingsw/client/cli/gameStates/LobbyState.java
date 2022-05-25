package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.StateManager;
import it.polimi.ingsw.client.cli.utilities.PrintableStrings;
import it.polimi.ingsw.model.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LobbyState implements State {
    private final CLI cli;

    public LobbyState(CLI cli) {
        this.cli = cli;
    }

    private boolean invalidChoice(int choice) {
        return choice != 0 && choice != 1;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        int choice;

        System.out.println(PrintableStrings.lobbyOptions);
        do {
            System.out.println("Enter your choice: ");
            in.reset();
            try {
                choice = in.nextInt();

                if (invalidChoice(choice)) {
                    System.out.println("This option is not valid.");
                }
            } catch (InputMismatchException e) {
                System.out.println("You have to enter an integer.");
                choice = -1;
                in.next();
            }
        } while (invalidChoice(choice));

        if (choice == 0) {
            new Thread(new StateManager(cli, new JoinGameState(cli))).start();
        } else if (choice == 1) {
            new Thread(new StateManager(cli, new CreateGameState(cli))).start();
        }
    }
}
