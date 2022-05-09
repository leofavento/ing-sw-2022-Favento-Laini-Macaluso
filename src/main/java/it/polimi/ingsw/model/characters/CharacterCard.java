package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;

public abstract class CharacterCard {

    int cost;
    boolean isActive=false;
    String UsedBy=null;

    public int getCost(){return cost;}

    public void increaseCost(){
        this.cost++;
    }

    public void setActive(){
        this.isActive=true;
    }

    public void setInactive(){
        this.isActive=false;
    }

    public boolean getActive(){return isActive;}

    public void setUsedBy(String Player){
        this.UsedBy=Player;
    }

    public void resetUsedBy(){
        this.UsedBy=null;
    }

    public String getUsedBy(){return UsedBy;}

}
