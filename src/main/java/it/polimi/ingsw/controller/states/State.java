package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;

/**
 * Interface that represent a generic state
 */
public interface State {

    /**
     * method used to pass control to the next state
     */
    void nextState();

    /**
     * method used to execute a generic action
     */
    void execute();

    /**
     * method used to execute an action based on the received message.
     * @param message the message received
     * @param sender the sender nickname
     */
    void receiveMessage(Message message, String sender);
}
