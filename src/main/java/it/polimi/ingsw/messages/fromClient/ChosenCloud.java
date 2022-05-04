package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Cloud;

public class ChosenCloud implements Message {
    private final Cloud cloud;

    public ChosenCloud(Cloud cloud){
        this.cloud = cloud;
    }

    public Cloud getCloud() {
        return cloud;
    }
}
