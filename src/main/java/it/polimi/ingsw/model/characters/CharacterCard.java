package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;

public interface CharacterCard {
    int cost=0;
    String usedBy=null;
    boolean isActive=false;

    void increaseCost();

    void setInitialCost(int c);

    void effect(Game game);

    int getCost();

    void setActive();

    void setInactive();

    void setUsedBy(String player);

}
