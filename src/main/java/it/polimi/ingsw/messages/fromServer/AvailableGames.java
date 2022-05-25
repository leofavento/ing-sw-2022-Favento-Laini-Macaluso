package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInfo;

import java.util.ArrayList;

public class AvailableGames implements FromServerMessage {
    private final ArrayList<GameInfo> availableGames;

    public AvailableGames(ArrayList<GameInfo> availableGames) {
        this.availableGames = availableGames;
    }

    public ArrayList<GameInfo> getAvailableGames() {
        return availableGames;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
