package it.polimi.ingsw.model.characters;

public enum Type2 {

    CARD1(1), //Take 1 student and place on an Island

    CARD7(1), //Take up to 3 students and replace them with the same number from your Entrance

    CARD11(2); //Take 1 student and place it into Dining Room

    private final int cost;
    Type2(int c){
        this.cost = c;
    }

    public int getCost(){
        return cost;
    }
}
