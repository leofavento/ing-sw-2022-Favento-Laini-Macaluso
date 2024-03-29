package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.Island;

/**
 * Message sent by the server to all the players to provide the nickname of the island owner after calculating the influence.
 */
public class IslandOwner implements FromServerMessage {
    private final Island island;
    private final String nickname;

    public IslandOwner(Island island, String nickname){
        this.island = island;
        this.nickname = nickname;
    }

    public Island getIsland() {
        return island;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
