package it.polimi.ingsw.model;

public abstract class Character {
    private int cost=0;
    private String usedBy=null;
    private boolean isActive=false;

    public void increaseCost(){
        this.cost ++;
    }

    public void setInitialCost(int c){
        this.cost=c;
    }

    public void effect() {

    }

    public int getCost(){
        return cost;
    }

    public void setActive(){
        this.isActive=true;
    }

    public void setInactive(){
        this.isActive=false;
    }

    public void setUsedBy(String player){
        this.usedBy=player;
    }

}
