package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Map;

public class AvailableAssistants implements Message {
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
}
