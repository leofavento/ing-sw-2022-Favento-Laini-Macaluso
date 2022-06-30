package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

/**
 * Message sent by the server to track client disconnection.
 */

public class Ping implements FromServerMessage {
    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
