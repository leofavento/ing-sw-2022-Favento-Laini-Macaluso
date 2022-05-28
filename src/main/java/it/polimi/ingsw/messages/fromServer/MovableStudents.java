package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

/**
 * Message sent by server to give the list of all movable students.
 */
public class MovableStudents implements FromServerMessage {
    private final ArrayList<Color> students;

    public MovableStudents(ArrayList<Color> students) {
        this.students = students;
    }

    public ArrayList<Color> getStudents() {
        return students;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
