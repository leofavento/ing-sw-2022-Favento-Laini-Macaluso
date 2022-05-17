package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.observer.Observable;

public interface State extends Observable<Message>{
    void nextState();
    void execute();
    void receiveMessage(Message message, String sender);
}
