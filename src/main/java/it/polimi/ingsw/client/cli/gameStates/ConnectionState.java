package it.polimi.ingsw.client.cli.gameStates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.exceptions.InvalidInputException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConnectionState extends State{
    private Client client;

    Scanner in = new Scanner(System.in);

    @Override
    public void doAction(CLI cli) throws IOException {

        System.out.println("Insert the server IP address you want to connect to: ");
        in.reset();
        String ip = in.next();

        System.out.println("Insert the server port: ");
        in.reset();
        int port = in.nextInt();

        this.client = new Client(true);
        client.startConnection(port, ip);
        cli.setGameState(new NicknameState());
    }
}
