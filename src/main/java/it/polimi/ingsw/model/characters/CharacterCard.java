package it.polimi.ingsw.model.characters;

public interface CharacterCard {
    int cost=0;
    String usedBy=null;
    boolean isActive=false;

    void increaseCost();

    void setInitialCost(int c);

    void effect();

    int getCost();

    void setActive();

    void setInactive();

    void setUsedBy(String player);

}
