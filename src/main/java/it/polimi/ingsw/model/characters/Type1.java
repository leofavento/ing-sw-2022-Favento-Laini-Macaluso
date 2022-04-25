package it.polimi.ingsw.model.characters;

public enum Type1 {

    CARD3(3), //Choose an island and apply influence

    CARD5(2), //Place a no entry tile on an island

    CARD6(3), //Towers do not count towards influence

    CARD8(2), //You count as having 2 more influence

    CARD9(3); //Choose a color. During influence, that color adds no influence

    private final int cost;
    Type1(int c){
        this.cost = c;
    }

    public int getCost(){
        return cost;
    }
}
