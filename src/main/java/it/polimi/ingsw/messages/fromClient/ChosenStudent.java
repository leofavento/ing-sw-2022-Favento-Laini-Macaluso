package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Color;

/**
 * Message sent by the client to select the student from the list provided by the server, or the color to choose (in case of the Character 9 activation).
 */

public class ChosenStudent implements Message {
    private final Color student;

    public ChosenStudent(Color student) {
        this.student = student;
    }

    public Color getStudent() {
        return student;
    }
}
