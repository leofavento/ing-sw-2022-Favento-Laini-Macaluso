package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Char9State {
    public void run(CLI cli) {
        Scanner in = new Scanner(System.in);
        int selection;

        while (!cli.isSuccess()) {
            if (cli.getView().getColors() == null) {
                try {
                    synchronized (cli.getView()) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ArrayList<Color> colors = cli.getView().getColors();

            System.out.println("Select a color: ");

            for (Color color : colors) {
                System.out.printf("%d --> %s %n",
                        color.ordinal() + 1,
                        color.toString().substring(0, 1).toUpperCase() + color.toString().substring(1));
            }
            try {
                in.reset();
                selection = in.nextInt();
                Color chosenColor = null;
                for (Color color : Color.values()) {
                    if (color.ordinal() + 1 == selection) {
                        chosenColor = color;
                    }
                }

                if (cli.getView().getColors().contains(chosenColor)) {
                    cli.getClient().sendMessage(new ChosenStudent(chosenColor));
                } else {
                    System.out.println("Please enter a valid student.");
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.println("Please enter an integer according to your choice.");
            }
            if (!cli.isSuccess()) {
                try {
                    synchronized (cli.getView()) {
                        cli.getGameState().wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (cli.isSuccess()) {
            cli.setSuccess(false);
        }
        System.out.println("You successfully activated Char9.");
    }

}