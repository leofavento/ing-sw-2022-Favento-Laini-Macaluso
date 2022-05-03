package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observer;

public class VirtualView implements Observer<Message> {
    ServerClientConnection serverClientConnection;
    private Player player;

    public VirtualView(ServerClientConnection serverClientConnection) {
        this.serverClientConnection = serverClientConnection;

        serverClientConnection.addObserver(this);
    }

    @Override
    public void update(Message message) {

    }
}
