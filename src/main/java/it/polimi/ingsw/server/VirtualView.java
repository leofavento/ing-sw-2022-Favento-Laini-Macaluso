package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observer;

public class VirtualView implements Observer<Message> {
    ServerClientConnection serverClientConnection;
    private final String nickname;
    private final GameHandler gameHandler;

    public VirtualView(ServerClientConnection serverClientConnection, String nickname, GameHandler gameHandler) {
        this.serverClientConnection = serverClientConnection;
        this.nickname = nickname;
        this.gameHandler = gameHandler;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    @Override
    public void update(Message message) {
        serverClientConnection.sendMessage(message);
    }
}
