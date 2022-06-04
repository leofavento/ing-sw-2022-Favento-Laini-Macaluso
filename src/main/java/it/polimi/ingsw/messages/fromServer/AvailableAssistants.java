package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Map;

/**
 * Message sent by server to a specific player, containing the list of assistants still not played.
 */
public class AvailableAssistants implements FromServerMessage {
    private final ArrayList<Assistant> availableAssistants;
    private final Map<Player, Assistant> playedAssistants;

    public AvailableAssistants(ArrayList<Assistant> availableAssistants, Map<Player, Assistant> playedAssistants) {
        this.availableAssistants = availableAssistants;
        this.playedAssistants = playedAssistants;
    }

    public ArrayList<Assistant> getAvailableAssistants() {
        return availableAssistants;
    }

    public Map<Player, Assistant> getPlayedAssistants() {
        return playedAssistants;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
