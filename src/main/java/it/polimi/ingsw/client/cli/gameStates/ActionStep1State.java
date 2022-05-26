package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.SchoolBoardRenderer;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.model.Tower;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ActionStep1State implements State {
    private final CLI cli;
    int selection;

    public ActionStep1State(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {

        cli.getClient().sendMessage(new Ack());

        Scanner in = new Scanner(System.in);
        String c;

        while (!cli.isSuccess()) {
            while (cli.getView().getMovableStudents() == null) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            IslandsRenderer.islandsRenderer(cli.getView().getDashboard());
            SchoolBoardRenderer.renderAllSchoolBoards(cli.getView().getPlayers());

            ArrayList<Color> movableStudents = cli.getView().getMovableStudents();

            System.out.println("Select a student to move, these are the movable students (type the corresponding color number): ");
            for (Color color : Color.values()) {
                System.out.printf("%d --> %s: " + movableStudents.stream().filter(a -> a == color).count() + "%n",
                        color.ordinal() + 1,
                        color.toString().substring(0,1).toUpperCase() + color.toString().substring(1));
            }

            in.reset();
            try {
                selection = in.nextInt();
                Color chosenStudent = null;
                for (Color color : Color.values()) {
                    if (color.ordinal() + 1 == selection) {
                        chosenStudent = color;
                    }
                }
                if (cli.getView().getMovableStudents().contains(chosenStudent)) {
                    cli.getClient().sendMessage(new ChosenStudent(chosenStudent));
                    cli.getView().setMovableStudents(null);
                } else {
                    cli.getView().setMovableStudents(null);
                    cli.getClient().sendMessage(new ChosenStudent(null));
                    continue;
                }
            } catch (InputMismatchException e) {
                in.next();
                cli.getView().setMovableStudents(null);
                cli.getClient().sendMessage(new ChosenStudent(null));
                continue;
            }

            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Do you want to move the selected student to an island or to the dining room?");
            System.out.println("Type 'i' or 'd': ");

            in.reset();
            c = in.next();

            try {
                if (c.equals("i")) {
                    IslandsRenderer.islandsRenderer(cli.getView().getDashboard());

                    System.out.printf("Choose one island from above (type the island number, from 1 to %d): \n", cli.getView().getDashboard().getIslands().size());
                    in.reset();
                    selection = in.nextInt();

                    cli.getClient().sendMessage(new ChosenDestination(selection));
                } else if (c.equals("d")) {
                    cli.getClient().sendMessage(new ChosenDestination(0));
                } else {
                    cli.getClient().sendMessage(new ChosenDestination(-1));
                }
            } catch (InputMismatchException e) {
                cli.getClient().sendMessage(new ChosenDestination(-1));
                in.next();
            }

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

        System.out.println("End of Move 1.");
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
