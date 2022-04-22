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

    public int countInfluence(Tower tower){
        int influence = 0;
        ArrayList<Color> colors = new ArrayList<>();

        influence += (tower == towerColor) ? numUnits : 0;

        for (Player p : tower.getTeamComponents()) {
            colors.addAll(p.getSchoolBoard()
                    .getProfessors()
                    .stream()
                    .map(Professor::getColor)
                    .collect(Collectors.toList()));
            influence += getExtraInfluence(p);
        }

        for (Student s : students) {
            if (colors.contains(s.getColor())) {
                influence++;
            }
        }

        return influence;
    }

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
