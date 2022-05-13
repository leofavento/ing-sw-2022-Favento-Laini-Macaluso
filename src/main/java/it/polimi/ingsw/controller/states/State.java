package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observable;

public interface State extends Observable<Message>{
    State nextState();
    void execute(Game game);
    void receiveMessage(Message message, String sender);
}
