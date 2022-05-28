package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.UseCharacterEffect;
import it.polimi.ingsw.model.characters.CharacterEnum;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ActivateCharactersState {
    public void run(CLI cli) {
        Scanner in = new Scanner(System.in);
        int choice;
        CharacterEnum selectedChar = null;

        while (!cli.isSuccess()) {
            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            System.out.println("Select your choice:");

            System.out.println("0 --> go back to your turn");
            for (int i = 0; i < 3; i++) {
                System.out.printf("%d --> activate %s: " + (i + 1) + "%n",
                        cli.getView().getDashboard().getCharacters()[i].getValue());
            }

            try {
                choice = in.nextInt();
                if (choice == 0) {
                    cli.setSuccess(true);
                } else {
                    selectedChar = cli.getView().getDashboard().getCharacters()[choice - 1].getValue();
                    cli.getClient().sendMessage(new UseCharacterEffect(selectedChar));
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.println("You have to enter an integer.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a valid number.");
            }

            if (!cli.isSuccess()) {
                try {
                    synchronized (cli.getGameState()) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (cli.isSuccess()) {
            cli.setSuccess(false);
        }

        if (selectedChar != null) {
            switch(selectedChar) {
                case Char1 -> {
                    Char1State charState = new Char1State();
                    charState.run(cli);
                }
                case Char3 -> {
                    Char3State charState = new Char3State();
                    charState.run(cli);
                }
                case Char5 -> {
                    Char5State charState = new Char5State();
                    charState.run(cli);
                }
                case Char7 -> {

                }
                case Char9 -> {

                }
                case Char10 -> {

                }
                case Char11 -> {

                }
                case Char12 -> {

                }
            }
        }

    }
}
