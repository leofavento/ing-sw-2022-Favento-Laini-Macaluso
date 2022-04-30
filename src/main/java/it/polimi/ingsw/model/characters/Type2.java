package it.polimi.ingsw.model.characters;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

//Characters that affect Students

public enum Type2 implements CharacterCard {

    CARD1(1), //Take 1 student and place on an Island

    CARD7(1), //Take up to 3 students and replace them with the same number from your Entrance

    CARD11(2); //Take 1 student and place it into Dining Room

    private int cost;
    private final ArrayList<Color> students;

    Type2(int c) {
        this.cost = c;
        students=new ArrayList<>();
    }

    @Override
    public void increaseCost() {
        cost++;
    }

    @Override
    public void setInitialCost(int c) {
    }

    @Override
    public void effect(Game game) {
    }

    @Override
    public int getCost() {
        return cost;
    }


    @Override
    public void setActive() {
    }

    @Override
    public void setInactive() {
    }

    @Override
    public void setUsedBy(String player) {
    }

    public void addStudent(Color s){
        students.add(s);
    }

    public Color takeStudent(int n){
        Color t = students.get(n);
        students.remove(n);
        return t;
    }
}

