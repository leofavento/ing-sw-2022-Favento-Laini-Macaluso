package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.player.DiningRoom;

import java.util.ArrayList;

public class WhereToMove implements FromServerMessage {
    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
