package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;

public interface State {
    void nextState();
    void execute();
    void receiveMessage(Message message, String sender);
}
