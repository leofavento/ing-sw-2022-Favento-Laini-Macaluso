package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Assistant;

public class PlayedAssistant implements Message {
    private final Assistant assistant;
    private final String player;

    public PlayedAssistant(Assistant assistant, String player){
        this.assistant = assistant;
        this.player = player;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public String getPlayer() {
        return player;
    }
}
