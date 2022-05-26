package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Assistant;

/**
 * Message by the server to communicate the assistant played and the corresponding player.
 */
public class PlayedAssistant implements FromServerMessage {
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

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
