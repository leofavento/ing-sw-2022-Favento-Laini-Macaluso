package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.TimedOutException;
import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String nickname;


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


    public void startConnection(int port, String IP_address, String nickname) throws IOException {
        clientSocket = new Socket(IP_address, port);

        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());


    }

    public void sendMessage(Message message) {
        try {
            output.reset();
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAndWait(Message message, int timeoutSeconds) throws TimedOutException{
        //TODO
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

    public static void main(String[] args) {
        //TODO
    }

}
