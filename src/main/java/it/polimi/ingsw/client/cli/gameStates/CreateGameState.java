package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.SetGame;

import java.util.Scanner;

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
                numOfPlayers = in.nextInt();
            } while (numOfPlayers < 0 || numOfPlayers > 4);
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
            cli.setGameState(new WaitingPlayersState(cli));
        }
    }
}