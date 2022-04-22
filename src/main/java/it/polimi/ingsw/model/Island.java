package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Island {
    private int numUnits;
    private final ArrayList<Student> students;
    // private ArrayList<Island> addedIslands;
    private Tower towerColor;
    private boolean noEntry;
    private final int ID;
    private final Map<Player, Integer> extraInfluence;


    public Island(int ID) {
        numUnits = 1;
        students = new ArrayList<>();
        // addedIslands = new ArrayList<Island>();
        extraInfluence = new HashMap<>();
        this.ID= ID;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addIsland(Island isl){
        // addedIslands.add(isl);
        numUnits += 1;
        for (Student s : isl.students) { // visibility error?
            addStudent(s);
        }
    }

    // TODO
    /*public int countInfluence(Player p){
        int influencevalue=0;

    }*/

    public Tower getTowerColor(){
        return towerColor;
    }

    public void setTowers(Tower t){
        this.towerColor= t;
    }

    public boolean hasTower(){
        return towerColor != null;
    }

    public void enableNoEntry(){
        this.noEntry= true;
    }

    public boolean getNoEntry(){
        return noEntry;
    }

    public int getID() {
        return ID;
    }

    public void setExtraInfluence(Player p, int influence) {
        extraInfluence.put(p, influence);
    }

    public int getExtraInfluence(Player p) {
        return extraInfluence.get(p);
    }

    public void resetExtraInfluences() {
        extraInfluence.clear();
    }
}
