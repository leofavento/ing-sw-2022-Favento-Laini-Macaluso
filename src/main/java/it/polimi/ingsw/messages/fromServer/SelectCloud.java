package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Cloud;

import java.util.ArrayList;

/**
 * Message sent by the server to the player to select a cloud.
 */
public class SelectCloud implements FromServerMessage {

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
