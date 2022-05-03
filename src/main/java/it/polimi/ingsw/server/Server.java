package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int socketPort = 2000;
    private ServerSocket socket;

    public Server() throws IOException {
        socket = new ServerSocket(socketPort);
    }

    public int getSocketPort() {
        return socketPort;
    }

    @Override
    public void run() {
        System.out.println("Server running on address " + socket.getInetAddress());
        System.out.println("and on port " + socket.getLocalPort());

        while(true) {
            try {
                Socket client = socket.accept();
                ServerClientConnection socketConnection = new ServerClientConnection(this, client);
                new Thread(socketConnection).start();
            } catch (IOException e) {
                System.out.println("Connection dropped.");
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }
}
