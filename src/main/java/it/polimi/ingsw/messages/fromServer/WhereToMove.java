package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

/**
 * Message sent by the server to ask the client where to move the student.
 */
public class WhereToMove implements FromServerMessage {
    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
