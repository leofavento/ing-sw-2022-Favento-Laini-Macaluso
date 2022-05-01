package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.Professor;
import it.polimi.ingsw.model.Tower;

import java.util.ArrayList;

public class SchoolBoard {
    private final DiningRoom diningRoom;
    private int numTower;
    private Tower towerColor;
    private int coins;
    private final Entrance entrance;
    private final ArrayList<Professor> professors;

    public SchoolBoard(){
        diningRoom = new DiningRoom();
        numTower = 0;
        coins = 0;
        entrance = new Entrance();
        professors = new ArrayList<>();
    }

    public void setTowerColor(Tower tower){
        this.towerColor = tower;
    }

    public Tower getTowerColor(){
        return towerColor;
    }

    public int getTowersNumber(){
        return numTower;
    }

    public void setTowersNumber(int numTower){
        this.numTower=numTower;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public void addCoin(){
        this.coins++;
    }

    public int getCoins(){
        return coins;
    }

    public void spendCoins(int coins){
        this.coins= (this.coins - coins);
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public void addProfessor(Professor p) {
        professors.add(p);
    }

    public void removeProfessor(Professor p) {
        professors.remove(p);
    }
}
