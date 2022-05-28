package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.UseCharacterEffect;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ActivateCharactersState {
    public static void run(CLI cli) {
        Scanner in = new Scanner(System.in);
        int choice;

        while (!cli.isSuccess()) {
            System.out.println("Select your choice:");

            System.out.println("0 --> go back to your turn");
            for (int i = 0; i < 3; i++) {
                System.out.printf("%d --> activate %s: " + (i + 1) + "%n",
                        cli.getView().getDashboard().getCharacters()[i].getValue());
            }

            try {
                choice = in.nextInt();

                switch (choice) {
                    case 0 -> cli.setSuccess(true);
                    case 1 -> cli.getClient().sendMessage(new UseCharacterEffect(cli.getView().getDashboard().getCharacters()[0].getValue()));
                    case 2 -> cli.getClient().sendMessage(new UseCharacterEffect(cli.getView().getDashboard().getCharacters()[1].getValue()));
                    case 3 -> cli.getClient().sendMessage(new UseCharacterEffect(cli.getView().getDashboard().getCharacters()[2].getValue()));
                    default -> System.out.println("Enter a valid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("You have to enter an integer.");
            }
        }
    }
}
