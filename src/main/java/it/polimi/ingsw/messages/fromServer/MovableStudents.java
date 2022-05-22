package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

public class MovableStudents implements Message {
    private final ArrayList<Color> students;

    public MovableStudents(ArrayList<Color> students) {
        this.students = students;
    }

    public ArrayList<Color> getStudents() {
        return students;
    }
}
