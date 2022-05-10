package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Message;

public abstract class Client {

    public abstract void sendMessage(Message message);

    public abstract void readMessage();

    public abstract void disconnect();
}
