package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.EnumMap;

public class SchoolBoard {
    private final EnumMap<Color, Integer> dining_room;
    private int numTower;
    private Tower towerColor;
    private int coins;
    private final ArrayList<Student> entrance;
    private final ArrayList<Professor> professors;

    public SchoolBoard(){
        this.dining_room = new EnumMap<>(Color.class);
        dining_room.put(Color.YELLOW, 0);
        dining_room.put(Color.BLUE, 0);
        dining_room.put(Color.GREEN, 0);
        dining_room.put(Color.RED, 0);
        dining_room.put(Color.PINK, 0);

        this.numTower=0;
        this.coins=0;

        this.entrance= new ArrayList<>();
        professors = new ArrayList<>();
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
        dining_room.put(student.getColor(), dining_room.get(student.getColor()) + 1);
    }

    public ArrayList<Student> getFromEntrance(){
        return entrance;
    }

    public int getColor(Color color){
        return dining_room.get(color);
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
