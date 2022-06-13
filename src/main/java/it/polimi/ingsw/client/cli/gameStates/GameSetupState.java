package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.model.Tower;

import java.util.*;


/**
 * In the CreateGame phase, the player chooses the desired tower and wizard.
 */
public class GameSetupState implements State {
    private final CLI cli;

    public GameSetupState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        int choice;
        Tower chosenTower = null;

        System.out.printf("Game settings: %d players, expert mode %s.%n",
                cli.getView().getTotalPlayers(),
                cli.getView().isExpertMode() ? "enabled" : "disabled");
        System.out.println();

        if (cli.getView().getAvailableTowers() == null) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (!cli.isSuccess()) {
            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }
            System.out.println("Pick a tower to enter a team:");
            printAvailableTowers();
            in.reset();
            try {
                choice = in.nextInt();
                for (Tower tower : cli.getView().getAvailableTowers().keySet()) {
                    if (tower.ordinal() == choice) {
                        chosenTower = tower;
                    }
                }
                cli.getClient().sendMessage(new ChosenTower(chosenTower));
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter the number of your chosen tower.");
                in.next();
            }
        }
        if (cli.isSuccess()) {
            cli.setSuccess(false);
            System.out.println("Waiting for the other players to make their decision...");
            cli.getView().setAvailableTowers(null);
        }

        if (cli.getView().getAvailableWizards() == null) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(!cli.isSuccess()) {
            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }
            System.out.println("Enter the number of your desired Wizard:");
            printAvailableWizards();
            in.reset();
            try {
                choice = in.nextInt();
                cli.getClient().sendMessage(new ChosenWizard(choice));
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter the number of your chosen wizard.");
                in.next();
            }
        }
        if (cli.isSuccess()) {
            cli.setSuccess(false);
            if (!cli.isLastPlayer()) {
                System.out.println("Waiting for the other players to make their decision...");
            }
            cli.getView().setRoundNumber(0);
            cli.getView().setAvailableWizards(null);
        }
    }

    private void printAvailableTowers() {
        HashMap<Tower, Integer> availableTowers = cli.getView().getAvailableTowers();

        System.out.println("+--------------------------------------------+\n" +
                "+-------TOWER-----------POSITIONS LEFT-------+");
        for (Tower color : availableTowers.keySet()) {
            System.out.printf("|-------%s--------------------%d-----------|%n",
                    color.toString(), availableTowers.get(color));
        }
        System.out.println("+--------------------------------------------+");
        for (Tower tower : availableTowers.keySet()) {
            System.out.printf("Enter %d for team %s%n", tower.ordinal(), tower);
        }
    }

    private void printAvailableWizards() {
        ArrayList<Integer> availableWizards = cli.getView().getAvailableWizards();
        System.out.print("Available wizards: ");
        for(Integer i : availableWizards) {
            System.out.printf("%d", i);
            if (availableWizards.indexOf(i) == availableWizards.size() - 1) {
                System.out.println();
            } else {
                System.out.print(", ");
            }
        }
    }
}
