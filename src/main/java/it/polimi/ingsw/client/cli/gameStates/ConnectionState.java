package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.exceptions.InvalidInputException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConnectionState implements State{
    Scanner in = new Scanner(System.in);

    @Override
    public void doAction(CLI cli) {

        System.out.println("Insert the server IP address you want to connect to: ");
        in.reset();
        String ip = in.next();

        System.out.println("Insert the server port: ");
        in.reset();
        int port = in.nextInt();

        cli.setClient(new Client(true));
        try {
            cli.getClient().startConnection(port, ip);
            cli.setGameState(new NicknameState());
            new Thread(cli.getClient()).start();
        } catch(IOException e) {
            System.out.println("The client can't connect to the specified server.");
        }
    }
}
