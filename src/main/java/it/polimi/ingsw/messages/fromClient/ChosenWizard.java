package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

/**
 * Message sent by the client to communicate the selected wizard.
 */

public class ChosenWizard implements Message {
    private final int wizardID;

    public ChosenWizard(int wizardID) {
        this.wizardID = wizardID;
    }

    public int getChosenWizard() {
        return wizardID;
    }
}
