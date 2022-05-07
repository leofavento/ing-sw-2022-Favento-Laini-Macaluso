package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class MotherNatureSteps implements Message {
    private final int maxStepsAllowed;

    public MotherNatureSteps(int maxStepsAllowed) {
        this.maxStepsAllowed = maxStepsAllowed;
    }

    public int getMaxStepsAllowed() {
        return maxStepsAllowed;
    }
}
