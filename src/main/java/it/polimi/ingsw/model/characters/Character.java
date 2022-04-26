package it.polimi.ingsw.model.characters;

public interface Character {
    int cost=0;
    String usedBy=null;
    boolean isActive=false;

    public void increaseCost();

    public void setInitialCost(int c);

    public void effect();

    public int getCost();

    public void setActive();

    public void setInactive();

    public void setUsedBy(String player);

}
