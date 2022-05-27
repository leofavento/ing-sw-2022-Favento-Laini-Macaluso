package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Cloud;

/**
 * Message sent by client to select the cloud from the list provided by the server.
 */

public class ChosenCloud implements Message {
    private final int cloud;

    public ChosenCloud(int cloud){
        this.cloud = cloud;
    }

    public int getCloud() {
        return cloud;
    }
}
