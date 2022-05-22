package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

public class SelectColor implements Message {
    private final ArrayList<Color> colors;

    public SelectColor(ArrayList<Color> colors) {
        this.colors = colors;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }
}