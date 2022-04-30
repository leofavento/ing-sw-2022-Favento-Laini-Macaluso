package it.polimi.ingsw.exceptions;

public class AlreadyPlayedAssistant extends Exception {

    @Override
    public String getMessage() {
        return ("This assistant may have already been played in a previous turn or by another player.");
    }
}
