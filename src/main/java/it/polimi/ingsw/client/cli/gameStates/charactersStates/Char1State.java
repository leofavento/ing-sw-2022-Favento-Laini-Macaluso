package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Char1State {
    public void run(CLI cli) {
        int selection;
        Scanner in = new Scanner(System.in);

        while (!cli.getView().getRequiredDestination()) {
            if (cli.getView().getMovableStudents() == null) {
                try {
                    synchronized (cli.getGameState()) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            IslandsRenderer.islandsRenderer(cli.getView().getDashboard());

            ArrayList<Color> movableStudents = cli.getView().getMovableStudents();

            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            System.out.println("Select a student to move from this character: ");
            for (Color color : Color.values()) {
                System.out.printf("%d --> %s: " + movableStudents.stream().filter(a -> a == color).count() + "%n",
                        color.ordinal() + 1,
                        color.toString().substring(0, 1).toUpperCase() + color.toString().substring(1));
            }

            try {
                in.reset();
                selection = in.nextInt();
                Color chosenStudent = null;
                for (Color color : Color.values()) {
                    if (color.ordinal() + 1 == selection) {
                        chosenStudent = color;
                    }
                }
                if (cli.getView().getMovableStudents().contains(chosenStudent)) {
                    cli.getClient().sendMessage(new ChosenStudent(chosenStudent));
                } else {
                    cli.getClient().sendMessage(new ChosenStudent(null));
                    continue;
                }
            } catch (InputMismatchException e) {
                in.next();
                cli.getClient().sendMessage(new ChosenStudent(null));
                continue;
            }
            if (!cli.getView().getRequiredDestination()) {
                try {
                    synchronized (cli.getGameState()) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        cli.getView().setRequiredDestination(false);
        while (!cli.isSuccess()) {
            IslandsRenderer.islandsRenderer(cli.getView().getDashboard());

            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            System.out.println("Type the number of the island where you want to move the selected student:");
            try {
                in.reset();
                selection = in.nextInt();
                cli.getClient().sendMessage(new ChosenDestination(selection));
            } catch (InputMismatchException e) {
                cli.getClient().sendMessage(new ChosenDestination(-1));
                in.next();
            }

            try {
                synchronized (cli.getGameState()) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (cli.isSuccess()) {
            cli.setSuccess(false);
            cli.getView().setMovableStudents(null);
        }

        System.out.println("You successfully activated Char1.");
    }
}
