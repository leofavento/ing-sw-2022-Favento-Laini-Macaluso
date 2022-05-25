package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenDestination;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class ActionStep1State implements State{
    private final CLI cli;
    int selection;

    public ActionStep1State(CLI cli){
        this.cli = cli;
    }

    @Override
    public void run(){

        cli.getClient().sendMessage(new Ack());
        try{
            synchronized (this){
                wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        while(!cli.isSuccess()){
            Scanner in = new Scanner(System.in);
            String c;

            ArrayList<Color> movableStudents = cli.getView().getMovableStudents();

            System.out.println("Select a student to move, these are the movable students (type the corresponding color number: ");

            System.out.println("1 --> Green: " + movableStudents.stream().filter(a->a == Color.GREEN).count());
            System.out.println("2 --> Red: " + movableStudents.stream().filter(a->a == Color.RED).count());
            System.out.println("3 --> Yellow: " + movableStudents.stream().filter(a->a == Color.YELLOW).count());
            System.out.println("4 --> Pink: " + movableStudents.stream().filter(a->a == Color.PINK).count());
            System.out.println("5 --> Blue: " + movableStudents.stream().filter(a->a == Color.BLUE).count());

            in.reset();
            selection = in.nextInt();

            if(selection == 1){
                cli.getClient().sendMessage(new ChosenStudent(Color.GREEN));
            }
            if(selection == 2){
                cli.getClient().sendMessage(new ChosenStudent(Color.RED));
            }
            if(selection == 3){
                cli.getClient().sendMessage(new ChosenStudent(Color.YELLOW));
            }
            if(selection == 4){
                cli.getClient().sendMessage(new ChosenStudent(Color.PINK));
            }
            if(selection == 5){
                cli.getClient().sendMessage(new ChosenStudent(Color.BLUE));
            }

            try {
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("Do you want to move the selected student to an island or to the dining room?");
            System.out.println("Type 'i' or 'd': ");

            in.reset();
            c = in.next();

            if(c.equals("i")){
                IslandsRenderer.islandsRenderer(cli.getView().getDashboard());

                System.out.printf("Choose one island from above (type the island number, from 1 to %d: \n", cli.getView().getDashboard().getIslands().size());
                in.reset();
                selection = in.nextInt();

                cli.getClient().sendMessage(new ChosenDestination(cli.getView().getDashboard().getIslands().get(selection-1)));

                try{
                    synchronized (this){
                        wait();
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            else if (c.equals("d")){
                //TODO
            }
        }

        if(cli.isSuccess()){
            cli.setSuccess(false);
        }
    }
}
