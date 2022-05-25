package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;

public class MotherNatureSteps implements FromServerMessage {
    private final int maxStepsAllowed;

    public MotherNatureSteps(int maxStepsAllowed) {
        this.maxStepsAllowed = maxStepsAllowed;
    }

    public int getMaxStepsAllowed() {
        return maxStepsAllowed;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
