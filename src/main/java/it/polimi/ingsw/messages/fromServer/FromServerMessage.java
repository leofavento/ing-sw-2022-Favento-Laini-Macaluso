package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.messages.Message;

public interface FromServerMessage extends Message {
    void receiveMessage(MessageReceiver messageReceiver);
}
