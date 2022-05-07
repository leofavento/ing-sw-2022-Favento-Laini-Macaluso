package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

public class MovableStudents implements Message {
    private final ArrayList<Color> entranceStudents;

    public MovableStudents(ArrayList<Color> entranceStudents) {
        this.entranceStudents = entranceStudents;
    }

    public ArrayList<Color> getEntranceStudents() {
        return entranceStudents;
    }
}
