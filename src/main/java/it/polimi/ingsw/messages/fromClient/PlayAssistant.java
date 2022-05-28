package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Assistant;

/**
 * Message sent by the client to play a selected assistant.
 */

public class PlayAssistant implements Message {
    private final Assistant assistant;

    public PlayAssistant(Assistant assistant){
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }
}
