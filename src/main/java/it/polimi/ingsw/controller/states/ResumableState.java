package it.polimi.ingsw.controller.states;

/**
 * Interface used to make a state resumable.
 */
public interface ResumableState extends State {
    void resume();
}
