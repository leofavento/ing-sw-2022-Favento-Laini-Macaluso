package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String nickname;
    private boolean active;


    /*@param cli true --> cli
    *            false --> gui*/
    public Client(Boolean cli){

        if(cli){
            //TODO
        }
        else {
            //TODO
        }
    }


    public void startConnection(int port, String IP_address) throws IOException {
        clientSocket = new Socket(IP_address, port);
        System.out.println("Client: started");
        System.out.println("Client socket: " + clientSocket);

        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());

        active = true;
    }

    public void sendMessage(Message message) {
        try {
            output.reset();
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            active = false;
        }
    }


    public void closeConnection(){
        try {
            clientSocket.close();
        } catch (IOException e){
        }
        try {
            input.close();
        } catch (IOException e) {

        }
        try {
            output.close();
        } catch (IOException e) {

        }
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    @Override
    public void run() {
        while(active) {
            try {
                Message received = (Message) input.readObject();
                //readMessage(received);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Connection closed from server.");
            }
        }
    }
}
