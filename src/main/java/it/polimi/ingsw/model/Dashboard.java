package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;

import java.util.ArrayList;

public class Dashboard {
    private final ArrayList<Island> islands;
    private final Character[] characters;
    private final ArrayList<Cloud> clouds;
    private final Professor[] professors;
    private final MotherNature motherNature;
    private final Bag bag;

    public Dashboard(){
        this.islands= new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.characters= new Character[3];
        this.professors= new Professor[5];
        professors[Color.YELLOW.ordinal()] = new Professor(Color.YELLOW);
        professors[Color.BLUE.ordinal()] = new Professor(Color.BLUE);
        professors[Color.GREEN.ordinal()] = new Professor(Color.GREEN);
        professors[Color.RED.ordinal()] = new Professor(Color.RED);
        professors[Color.PINK.ordinal()] = new Professor(Color.PINK);
        motherNature = new MotherNature();
        bag = new Bag();
    }

    public ArrayList<Island> getIslands(){
        return islands;
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    public void placeIslands(){
        for(int i = 0; i < 12; i++) {
            islands.add(new Island());
        }
    }

    public void placeCloudTiles(int number){
        for(int i = 0; i < number; i++) {
            clouds.add(new Cloud());
        }
    }

    public void moveMotherNature(int steps){
        motherNature.setIsland((motherNature.getIsland() + steps) % islands.size());
    }

    public void deleteIsland(Island island){
        islands.remove(island);
    }

    public int countTowers(Tower tower){
        return islands.stream()
                .filter(Island::hasTower)
                .filter(i -> i.getTowerColor() == tower)
                .mapToInt(Island::getNumUnits)
                .sum();
    }

    public void mergeIslands(Island a, Island ... merging) {
        for (Island t : merging) {
            a.addIsland(t);
            deleteIsland(t);
        }
        motherNature.setIsland(islands.indexOf(a));
    }

    public Professor[] getProfessors() {
        return professors;
    }

    public Bag getBag() {
        return bag;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }
}
