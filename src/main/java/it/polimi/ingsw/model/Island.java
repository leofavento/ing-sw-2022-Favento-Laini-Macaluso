package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Island {
    private int numUnits;
    private ArrayList<Student> students;
    private ArrayList<Island> addedIslands;
    private Tower towerColor;
    private boolean noEntry;
    private final int ID;

    public Island(int numUnits, int ID) {
        this.numUnits = numUnits;
        students = new ArrayList<Student>();
        addedIslands = new ArrayList<Island>();
        this.ID= ID;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addIsland(Island isl){
        addedIslands.add(isl);
    }

    //TODO
    //public int countInfluence(Player p){
        //int influencevalue=0;
        //da sistemare una volta aggiunte tutte le altre classi necessarie al conteggio
        //return influencevalue;
    //}

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

    public int getID(){return ID;}
}