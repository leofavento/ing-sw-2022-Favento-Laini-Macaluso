package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.SchoolBoardRenderer;
import it.polimi.ingsw.client.cli.gameStates.charactersStates.ActivateCharactersState;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenSteps;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ActionStep2State implements State {
    private final CLI cli;

    public ActionStep2State(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        int steps;

        cli.getView().setCurrentStatus(PlayerStatus.MOVE_2);
        cli.getClient().sendMessage(new Ack());

        if (cli.getView().getMotherNatureSteps() == 0) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (!cli.isSuccess()) {
            Scanner in = new Scanner(System.in);

            steps = cli.getView().getMotherNatureSteps();

            SchoolBoardRenderer.renderAllSchoolBoards(cli.getView().getPlayers(), cli.getView().isExpertMode());
            IslandsRenderer.islandsRenderer(cli.getView().getDashboard());

            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            if (cli.getView().getDashboard().getAdditionalMNMovements() > 0) {
                System.out.printf("How many steps do you want MotherNature to make? Choose a number between 1 and %d %s: %n",
                        steps,
                        "(additional Mother Nature movements included)");
            } else {
                System.out.printf("How many steps do you want MotherNature to make? Choose a number between 1 and %d: %n", steps);
            }
            if (cli.getView().isExpertMode() && cli.getView().getDashboard().getPlayedCharacter() == null) {
                System.out.println("Otherwise, type 0 to activate a character.");
            }

            try {
                in.reset();
                steps = in.nextInt();

                if (cli.getView().isExpertMode() && steps == 0 && cli.getView().getDashboard().getPlayedCharacter() == null) {
                    ActivateCharactersState activateCharacters = new ActivateCharactersState();
                    activateCharacters.run(cli);
                    continue;
                } else {
                    cli.getClient().sendMessage(new ChosenSteps(steps));
                }
                if (cli.getView().getCurrentStatus() == PlayerStatus.MOVE_2) {
                    try {
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        if (cli.isSuccess()) {
            cli.setSuccess(false);
            cli.getView().setMotherNatureSteps(0);
        }
        System.out.println("These are the islands after Move 2:");
        IslandsRenderer.islandsRenderer(cli.getView().getDashboard());
    }
}
