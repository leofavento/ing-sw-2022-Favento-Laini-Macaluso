package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String input;
        int port;

        System.out.println("Eriantys: Server");

        do {
            System.out.println("Enter the port you wish to use [d = default port -> 25565]:");
            input = in.next();
            if (isDefault(input)) {
                port = 25565;
            } else {
                try {
                    port = Integer.parseInt(input);
                    if (invalidPort(port)) {
                        System.out.println("The server port has to be a number between 1024 and 65535.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("The server port has to be a number between 1024 and 65535.");
                    port = 0;
                }
            }
        } while (invalidPort(port));

        Server server = new Server(port);
        new Thread(server).start();
    }

    private static boolean isDefault(String input) {
        return input.equalsIgnoreCase("d");
    }

    private static boolean invalidPort(int port) {
        return port < 1024 || port > 65535;
    }
}
