package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenSteps;
import it.polimi.ingsw.model.Assistant;

import java.util.Scanner;

public class ActionStep2State implements State{
    private final CLI cli;

    public ActionStep2State(CLI cli){
        this.cli = cli;
    }

    @Override
    public void run(){
        int steps;

        cli.getClient().sendMessage(new Ack());

        while (!cli.isSuccess()){
            Scanner in = new Scanner(System.in);

            try{
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            steps = cli.getView().getMotherNatureSteps();

            System.out.printf("How many steps do you want MotherNature to make? Choose a number between 1 and %d: \n", steps);

            in.reset();
            steps = in.nextInt();

            cli.getClient().sendMessage(new ChosenSteps(steps));

            try{
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}
