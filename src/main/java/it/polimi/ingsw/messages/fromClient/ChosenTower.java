package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Tower;

public class ChosenTower implements Message {
    private final Tower color;

    public ChosenTower(Tower color) {
        this.color = color;
    }

    public Tower getColor() {
        return color;
    }
}
