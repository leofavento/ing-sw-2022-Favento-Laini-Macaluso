package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Island;

public class IslandOwner implements Message {
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
}
