package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.CharacterRenderer;
import it.polimi.ingsw.messages.fromClient.UseCharacterEffect;
import it.polimi.ingsw.model.characters.CharacterEnum;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 * state used to activate the correct character state
 */

public class ActivateCharactersState {
    public void run(CLI cli) {
        Scanner in = new Scanner(System.in);
        int choice;
        CharacterEnum selectedChar = null;

        while (!cli.isSuccess()) {
            CharacterRenderer.renderAllCharacters(cli.getView().getDashboard().getCharacters());

            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            System.out.println("Select your choice:");

            System.out.println("0 --> go back to your turn");
            for (int i = 0; i < 3; i++) {
                System.out.println((i + 1) + " --> activate " +
                        cli.getView().getDashboard().getCharacters()[i].getValue() +
                        " (" + cli.getView().getDashboard().getCharacters()[i].getCost() + " coins required)");
            }
            System.out.println("You have " + Objects.requireNonNull(cli.getView().getPlayers().stream()
                    .filter(p -> Objects.equals(p.getNickname(), cli.getClient().getNickname()))
                    .findAny().orElse(null)).getSchoolBoard().getCoins() + " coins.");

            try {
                choice = in.nextInt();
                if (choice == 0) {
                    cli.setSuccess(true);
                    selectedChar = null;
                } else {
                    selectedChar = cli.getView().getDashboard().getCharacters()[choice - 1].getValue();
                    cli.getClient().sendMessage(new UseCharacterEffect(selectedChar));
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.println("You have to enter an integer.");
                continue;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            synchronized (cli.getGameState()) {
                while (!cli.isSuccess() && cli.getView().getLastErrorMessage() == null) {
                    try {
                        cli.getGameState().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        if (cli.isSuccess()) {
            cli.setSuccess(false);
        }

        if (selectedChar != null) {
            switch (selectedChar) {
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
                    Char7State charState = new Char7State();
                    charState.run(cli);
                }
                case Char9 -> {
                    Char9State charState = new Char9State();
                    charState.run(cli);
                }
                case Char10 -> {
                    Char10State charState = new Char10State();
                    charState.run(cli);
                }
                case Char11 -> {
                    Char11State charState = new Char11State();
                    charState.run(cli);
                }
                case Char12 -> {
                    Char12State charState = new Char12State();
                    charState.run(cli);
                }
            }
            System.out.println("You successfully activated " + selectedChar + ".");
        }
    }
}
