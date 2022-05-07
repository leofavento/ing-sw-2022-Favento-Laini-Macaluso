package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

import java.util.ArrayList;

public class AvailableWizards implements Message {
    private final ArrayList<Integer> availableWizards;

    public AvailableWizards(ArrayList<Integer> availableWizards) {
        this.availableWizards = availableWizards;
    }

    public ArrayList<Integer> getAvailableWizards() {
        return availableWizards;
    }
}
