package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

/**
 * Message sent by the client to select the number of steps from the options provided by the server.
 */

public class ChosenSteps implements Message {
    private final int steps;

    public ChosenSteps(int steps){
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }
}
