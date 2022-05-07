package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

public class ChosenWizard implements Message {
    private final int wizardID;

    public ChosenWizard(int wizardID) {
        this.wizardID = wizardID;
    }

    public int getChosenWizard() {
        return wizardID;
    }
}
