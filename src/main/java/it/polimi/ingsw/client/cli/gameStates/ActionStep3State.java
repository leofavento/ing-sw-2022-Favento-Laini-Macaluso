package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.CloudsRenderer;
import it.polimi.ingsw.client.cli.gameStates.charactersStates.ActivateCharactersState;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenCloud;
import it.polimi.ingsw.model.player.PlayerStatus;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This state implements controller's ActionStep3 for the command line interface
 * In this phase the Player can choose the cloud to get the students from or activate a character effect.
 */
public class ActionStep3State implements State{
    private final CLI cli;

    public ActionStep3State(CLI cli){
        this.cli = cli;
    }

    @Override
    public void run(){
        int selection;
        cli.getView().setCurrentStatus(PlayerStatus.MOVE_3);
        cli.getClient().sendMessage(new Ack());

        while (!cli.isSuccess()){
            Scanner in = new Scanner(System.in);

            CloudsRenderer.cloudRenderer(cli.getView().getDashboard());

            if (cli.getView().getLastErrorMessage() != null) {
                System.out.println(cli.getView().getLastErrorMessage().getMessage());
                cli.getView().setLastErrorMessage(null);
            }

            System.out.println("These are the available clouds, choose the one you want to get the students from: ");
            if (cli.getView().isExpertMode() && cli.getView().getDashboard().getPlayedCharacter() == null) {
                System.out.println("Otherwise, type 0 to activate a character.");
            }

            try{
                in.reset();
                selection = in.nextInt();

                if (cli.getView().isExpertMode() && selection == 0 && cli.getView().getDashboard().getPlayedCharacter() == null) {
                    ActivateCharactersState activateCharacters = new ActivateCharactersState();
                    activateCharacters.run(cli);
                    continue;
                } else {
                    cli.getClient().sendMessage(new ChosenCloud(selection - 1));
                }

                if (!cli.isSuccess()) {
                    try {
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (InputMismatchException e){
                System.out.println("Please enter a valid number.");
            }
        }

        if(cli.isSuccess()){
            cli.setSuccess(false);
        }
    }
}
