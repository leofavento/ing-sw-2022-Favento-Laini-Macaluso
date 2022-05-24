package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.model.Tower;

import java.util.HashMap;
import java.util.Scanner;

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

        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!cli.isSuccess()) {
            System.out.println("Pick a tower to enter a team:");
            printAvailableTowers();
            choice = in.nextInt();
            for (Tower tower : Tower.values()) {
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
        }
        if (cli.isSuccess()) {
            cli.setSuccess(false);
        }
        // richiesta wizard id
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
        for (Tower tower : Tower.values()) {
            if (availableTowers.containsKey(tower)) {
                System.out.printf("Enter %d for team %s%n", tower.ordinal(), tower);
            }
        }
    }
}
