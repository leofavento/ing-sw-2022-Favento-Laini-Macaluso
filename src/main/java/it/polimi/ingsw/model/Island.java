package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Island implements StudentDeposit {
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

    @Override
    public void addStudent(Color student) {
        students.add(student);
    }

    @Override
    public void extractStudent(Color color) throws StudentNotExistingException {
        if (! students.contains(color)) {
            throw new StudentNotExistingException("There is no " + color.name() + " student in this island.");
        } else {
            students.remove(color);
        }
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

    /**
     * This method calculates the influence of a team over the island
     * @param team contains the players belonging to a team. The method doesn't check if the players
     *             have actually the same tower color.
     * @return an integer representing the influence over the island
     */
    public int countInfluence(ArrayList<Player> team){
        int influence = 0;
        ArrayList<Color> colors = new ArrayList<>();
        Tower tower = team.get(0).getSchoolBoard().getTowerColor();

        influence += (tower == towerColor) ? numUnits : 0;

        for (Player p : team) {
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

    public int noColorInfluence(ArrayList<Player> team, Color c){
        int influence = 0;
        ArrayList<Color> colors = new ArrayList<>();
        Tower tower = team.get(0).getSchoolBoard().getTowerColor();

        influence += (tower == towerColor) ? numUnits : 0;

        for (Player p : team) {
            colors.addAll(p.getSchoolBoard()
                    .getProfessors()
                    .stream()
                    .map(Professor::getColor)
                    .collect(Collectors.toList()));
            influence += getExtraInfluence(p);
        }
        colors.remove(c);
        //

        for (Color s : students) {
            if (colors.contains(s)) {
                influence++;
            }
        }

        return influence;
    }

    public int noTowerInfluence(ArrayList<Player> team){
        int influence = 0;
        ArrayList<Color> colors = new ArrayList<>();

        for (Player p : team) {
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

    public int getNoEntry(){return noEntry;}

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
