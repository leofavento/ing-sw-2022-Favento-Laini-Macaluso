package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.controller.states.Planning;
import it.polimi.ingsw.messages.fromClient.PlayAssistant;
import it.polimi.ingsw.model.Assistant;

import java.util.ArrayList;
import java.util.Scanner;

public class PlanningState implements State{
    private final CLI cli;

    public PlanningState(CLI cli){
        this.cli = cli;
    }

    @Override
    public void run(){
        Scanner in = new Scanner(System.in);

        while(!cli.isSuccess()){

            int selection;
            Assistant selectedAssistant;

            try{
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            ArrayList<Assistant> availableAssistants = cli.getView().getAvailableAssistants();
            System.out.println("These are the available assistants");
            System.out.println("|---NAME------VALUE----MOVEMENTS---");
            for(int i=0; i<availableAssistants.size(); i++){
                System.out.println(i+1 + ":   " + availableAssistants.get(i).toString() +
                        "           " + availableAssistants.get(i).getValue() +
                        "           " + availableAssistants.get(i).getMovements());
            }
            System.out.println("|----------------------------------");
            System.out.println("Insert the number of the assistant you want to play: ");
            in.reset();
            selection = in.nextInt() - 1;

            selectedAssistant = availableAssistants.get(selection);

            cli.getClient().sendMessage(new PlayAssistant(selectedAssistant));
            try{
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }



        }
        if(cli.isSuccess()){
            cli.setSuccess(false);
        }
    }
}
