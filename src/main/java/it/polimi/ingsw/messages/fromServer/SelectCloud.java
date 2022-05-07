package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Cloud;

import java.util.ArrayList;

public class SelectCloud implements Message {
    private final ArrayList<Cloud> availableClouds;

    public SelectCloud(ArrayList<Cloud> availableClouds) {
        this.availableClouds = availableClouds;
    }

    public ArrayList<Cloud> getAvailableClouds() {
        return availableClouds;
    }
}
