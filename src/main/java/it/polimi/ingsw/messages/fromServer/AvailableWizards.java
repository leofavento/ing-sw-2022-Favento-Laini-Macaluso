package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

import java.util.ArrayList;

/**
 * Message sent by the server to inform the client about the wizards which are still available
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
