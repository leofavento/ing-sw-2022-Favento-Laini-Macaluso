package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

/**
 * Message sent by the server to the player to select a color.
 */
public class SelectColor implements FromServerMessage {

    public SelectColor(){}

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
