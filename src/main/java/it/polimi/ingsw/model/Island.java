package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Island {
    private int numUnits;
    private final ArrayList<Color> students;
    private Tower towerColor;
    private int noEntry;
    private final Map<Player, Integer> extraInfluence;


    public Island() {
        numUnits = 1;
        students = new ArrayList<>();
        extraInfluence = new HashMap<>();
        noEntry = 0;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public void addStudent(Color student) {
        students.add(student);
    }

    public void addIsland(Island isl){
        numUnits += 1;
        for (Color s : isl.students) { // visibility error?
            addStudent(s);
        }
        while(isl.useNoEntry()) {
            noEntry++;
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

        for (Color s : students) {
            if (colors.contains(s)) {
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

    public void addNoEntry(){
        noEntry++;
    }

    public boolean useNoEntry(){
        if (noEntry > 0) {
            noEntry--;
            return true;
        }
        else {
            return false;
        }

    }

    public void setExtraInfluence(Player p, int influence) {
        extraInfluence.put(p, influence);
    }

    public int getExtraInfluence(Player p) {
        Integer value = extraInfluence.get(p);
        return value == null ? 0 : value;
    }

    public void resetExtraInfluences() {
        extraInfluence.clear();
    }
}
