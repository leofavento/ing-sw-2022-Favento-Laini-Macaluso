package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.HashMap;

public class SchoolBoard {
    private HashMap<String, Integer> dining_room;
    private int numTower;
    private Tower towerColor;
    private int coins;
    private ArrayList<Student> entrance;


    public SchoolBoard(){
        this.dining_room = new HashMap<>();
        dining_room.put("yellow", 0);
        dining_room.put("blue", 0);
        dining_room.put("green", 0);
        dining_room.put("red", 0);
        dining_room.put("pink", 0);

        this.numTower=0;
        this.coins=0;

        this.entrance= new ArrayList<>();
    }

    public void setTowerColor(Tower t){
        this.towerColor=t;
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

    public void addToEntrance(Student student){
        entrance.add(student);
    }

    public void moveToDiningRoom(Student student){
        dining_room.replace(String.valueOf(student.getColor()), (dining_room.get(String.valueOf(student.getColor()))+1));
    }

    public ArrayList<Student> getFromEntrance(){
        return entrance;
    }

    public int getColor(Color color){
        return dining_room.get(String.valueOf(color));
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

}
