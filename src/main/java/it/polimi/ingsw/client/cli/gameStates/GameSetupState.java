package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
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
        }
    }

    private void printAvailableTowers() {
        HashMap<Tower, Integer> availableTowers = cli.getView().getAvailableTowers();

        System.out.println("+--------------------------------------------+\n" +
                "+-------TOWER-----------POSITIONS LEFT-------+");
        for (Tower color : availableTowers.keySet()) {
            System.out.printf("|-------%s--------------------%d-----------|",
                    color.toString(), availableTowers.get(color));
        }
        System.out.println("+--------------------------------------------+");
    }
}
