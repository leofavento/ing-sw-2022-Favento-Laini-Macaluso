package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int input = -1;

        System.out.println("Welcome to Eriantys.");
        System.out.println("Press 0 to run the CLI, or press 1 to run the GUI.");
        do {
            try {
                input = in.nextInt();
                if (invalidInput(input)) {
                    System.out.println("Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid option.");
                in.next();
            }
        } while (invalidInput(input));

        if (input == 0) {
            CLI cli = new CLI();
            new Thread(cli).start();
        } else {
            GUI.main(null);
        }
    }

    private static boolean invalidInput(int input) {
        return input < 0 || input > 1;
    }
}
