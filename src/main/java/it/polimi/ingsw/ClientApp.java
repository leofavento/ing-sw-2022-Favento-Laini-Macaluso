package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int input;

        System.out.println("Welcome to Eryantis.");
        System.out.println("Press 0 to run the CLI, or press 1 to run the GUI.");
        do {
            input = in.nextInt();
            if (invalidInput(input)) {
                System.out.println("Please enter a valid option.");
            }
        } while (invalidInput(input));

        if (input == 0) {
            CLI cli = new CLI();
            new Thread(cli).start();
        } else {
            // gui
        }
    }

    private static boolean invalidInput(int input) {
        return input < 0 || input > 1;
    }
}
