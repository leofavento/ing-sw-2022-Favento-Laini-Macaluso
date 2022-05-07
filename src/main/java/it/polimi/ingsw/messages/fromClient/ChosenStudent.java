package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Color;

public class ChosenStudent implements Message {
    private final Color student;

    public ChosenStudent(Color student) {
        this.student = student;
    }

    public Color getStudent() {
        return student;
    }
}
