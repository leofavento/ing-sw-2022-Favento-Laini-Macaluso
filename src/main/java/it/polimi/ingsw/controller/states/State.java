package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.model.Game;

public interface State {
    State nextState();
    void execute(Game game);
}
