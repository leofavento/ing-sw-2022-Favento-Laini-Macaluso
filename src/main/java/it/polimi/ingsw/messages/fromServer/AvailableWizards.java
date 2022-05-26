package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;

import java.util.ArrayList;

/**
 * Message sent by server to inform the client about wizards still available
 */
public class AvailableWizards implements FromServerMessage {
    private final ArrayList<Integer> availableWizards;

    public AvailableWizards(ArrayList<Integer> availableWizards) {
        this.availableWizards = availableWizards;
    }

    public ArrayList<Integer> getAvailableWizards() {
        return availableWizards;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
