package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.*;

import java.util.ArrayList;
import java.util.Collections;

public class Dashboard {
    private final ArrayList<Island> islands;
    private final CharacterCard[] characters;
    private final ArrayList<Cloud> clouds;
    private final Professor[] professors;
    private final Bag bag;
    private int motherNaturePosition;

    public Dashboard(){
        this.islands= new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.characters= new CharacterCard[3];
        this.professors= new Professor[5];
        professors[Color.YELLOW.ordinal()] = new Professor(Color.YELLOW);
        professors[Color.BLUE.ordinal()] = new Professor(Color.BLUE);
        professors[Color.GREEN.ordinal()] = new Professor(Color.GREEN);
        professors[Color.RED.ordinal()] = new Professor(Color.RED);
        professors[Color.PINK.ordinal()] = new Professor(Color.PINK);
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
        motherNaturePosition = (motherNaturePosition + steps) % islands.size();
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
        setMotherNature(a);
    }

    public Professor[] getProfessors() {
        return professors;
    }

    public Bag getBag() {
        return bag;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public void setMotherNature(Island island) {
        motherNaturePosition = islands.indexOf(island);
    }
    
    public void setCharacters(ArrayList<CharacterCard> c) {
        characters[0] = c.get(0);
        characters[1] = c.get(1);
        characters[2] = c.get(2);

        //Collections.addAll(allCharacters,c);
        //Collections.shuffle(gameCharacters);
        //for (int i = 0; i < characters.length; i++) {
          //  characters[i] = allCharacters.get(i);
        //}
    }

    public CharacterCard[] getCharacters() {
        return characters;
    }
}
