package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;

/**
 * Message sent by the server to the player to ask for Mother Nature movement.
 */
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
