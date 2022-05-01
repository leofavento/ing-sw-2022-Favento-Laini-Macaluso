package it.polimi.ingsw.controller.states;

public enum EnumState {
    SETUP(new Setup()),
    PLANNING(new Planning()),
    ACTION_STEP_1(new ActionStep1());

    private final State state;

    EnumState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
