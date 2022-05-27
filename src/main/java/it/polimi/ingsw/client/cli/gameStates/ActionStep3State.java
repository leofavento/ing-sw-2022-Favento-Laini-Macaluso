package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.cli.componentRenderer.CloudsRenderer;
import it.polimi.ingsw.controller.states.ActionStep3;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenCloud;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ActionStep3State implements State{
    private final CLI cli;

    public ActionStep3State(CLI cli){
        this.cli = cli;
    }

    @Override
    public void run(){
        ArrayList<Cloud> availableClouds;
        int selection;

        cli.getClient().sendMessage(new Ack());

        try{
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        while (!cli.isSuccess()){
            Scanner in = new Scanner(System.in);

            CloudsRenderer.cloudRenderer(cli.getView().getDashboard());

            System.out.println("These are the available clouds, choose the one you want to get the students from: ");
            CloudsRenderer.cloudRenderer(cli.getView().getDashboard());

            try{
                in.reset();
                selection = in.nextInt();

                cli.getClient().sendMessage(new ChosenCloud(selection-1));

                try{
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
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
