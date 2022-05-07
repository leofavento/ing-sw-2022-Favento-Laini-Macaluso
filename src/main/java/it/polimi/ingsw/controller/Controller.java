package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.EnumState;
import it.polimi.ingsw.controller.states.State;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

public class Controller {
    private final Game game;
    private State state;
    private boolean isLastRound;

    public Controller(Game game) {
        this.game = game;
        this.state = EnumState.SETUP.getState();
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean checkIfLastRound() {
        int necessaryStudents = 0;

        switch (game.getNumberOfPlayers()) {
            case (2):
                necessaryStudents = 6;
                break;
            case (3):
            case (4):
                necessaryStudents = 12;
                break;
            default:
                // throw exception?
        }

        isLastRound = necessaryStudents < game.getDashboard().getBag().getStudentsLeft();
        return isLastRound;
    }

    public boolean getIsLastRound() {
        return isLastRound;
    }

    public void updateTurnOrder() {
        game.getOnlinePlayers().sort((Player p1, Player p2) -> Integer.compare(p1.getPlayedAssistant().getValue(), p1.getPlayedAssistant().getValue()));
    }

    public State getState() {
        return state;
    }
}
