package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

public class ChosenSteps implements Message {
    private final int steps;

    public ChosenSteps(int steps){
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }
}
