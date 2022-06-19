package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.StateManager;
import it.polimi.ingsw.messages.fromClient.SetGame;

import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * In the CreateGame phase, the player chooses the number of players for the game and the
 * enabling of the expert mode.
 */
public class CreateGameState implements State {
    private final CLI cli;

    public CreateGameState(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        int numOfPlayers;
        String expertMode;

        while (!cli.isSuccess()) {
            do {
                System.out.println("Enter your desired number of players [2-4]:");
                in.reset();
                try {
                    numOfPlayers = in.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input");
                    numOfPlayers = 0;
                    in.next();
                }
            } while (numOfPlayers < 2 || numOfPlayers > 4);
            System.out.println("Do you wish to enable the expert mode? [y/n]");
            in.reset();
            expertMode = in.next();

            cli.getClient().sendMessage(new SetGame(numOfPlayers,
                    Character.toLowerCase(expertMode.charAt(0)) == 'y'));
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cli.getView().setHost(true);
            cli.getView().setTotalPlayers(numOfPlayers);
            cli.getView().setActivePlayers(1);
            cli.getView().setExpertMode(Character.toLowerCase(expertMode.charAt(0)) == 'y');
        }

        if (cli.isSuccess()) {
            cli.setSuccess(false);
            new Thread(new StateManager(cli, new WaitingPlayersState(cli))).start();
        }
    }
}