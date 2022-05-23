package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.utilities.PrintableStrings;
import it.polimi.ingsw.model.Color;

import java.util.Scanner;

public class LobbyState implements State {
    @Override
    public void doAction(CLI cli) {
        Scanner in = new Scanner(System.in);
        int choice;

        System.out.println(PrintableStrings.lobbyOptions);
        do {
            in.reset();
            choice = in.nextInt();

            if (invalidChoice(choice)) {
                System.out.println("This option is not valid.");
            }
        } while(invalidChoice(choice));

        if (choice == 0) {
            cli.setGameState(new JoinGameState());
        } else if (choice == 1) {
            cli.setGameState(new CreateGameState());
        }
    }

    private boolean invalidChoice(int choice) {
        return choice != 0 && choice != 1;
    }
}
