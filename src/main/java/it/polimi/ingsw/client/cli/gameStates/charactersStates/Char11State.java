package it.polimi.ingsw.client.cli.gameStates.charactersStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Char11State {

    public void run(CLI cli) {
        int selection;
        Scanner in = new Scanner(System.in);

        while (cli.getView().getMovableStudentsChar() == null) {
            try {
                synchronized (cli.getGameState()) {
                    cli.getGameState().wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayList<Color> movableStudentsChar = cli.getView().getMovableStudentsChar();
        while (!cli.isSuccess()) {
            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            System.out.println("Select a student to move from this character: ");
            for (Color color : Color.values()) {
                System.out.printf("%d --> %s: " + movableStudentsChar.stream().filter(a -> a == color).count() + "%n",
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
                if (cli.getView().getMovableStudentsChar().contains(chosenStudent)) {
                    cli.getClient().sendMessage(new ChosenStudent(chosenStudent));
                } else {
                    cli.getClient().sendMessage(new ChosenStudent(null));
                }
            } catch (InputMismatchException e) {
                in.next();
                cli.getClient().sendMessage(new ChosenStudent(null));
            }
            if (!cli.isSuccess()) {
                try {
                    synchronized (cli.getGameState()) {
                        cli.getGameState().wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (cli.isSuccess()) {
            cli.setSuccess(false);
            cli.getView().setMovableStudentsChar(null);
        }

    }
}
