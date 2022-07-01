package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the equivalent of the table in the physical game
 */
public class Dashboard implements Serializable {
    private final ArrayList<Island> islands;
    private CharacterCard playedCharacter;
    private final CharacterCard[] characters;
    private final ArrayList<Cloud> clouds;
    private final Professor[] professors;
    private final Bag bag;
    private int motherNaturePosition;
    private int additionalMNMovements;
    private boolean doNotCountTowers=false;
    private Color doNotCountColor=null;

    public Dashboard(){
        this.islands= new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.playedCharacter= null;
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

    /**
     * method used in the setup phase to generate the 12 islands
     */
    public void placeIslands(){
        for(int i = 0; i < 12; i++) {
            islands.add(new Island());
        }
    }

    /**
     * method used in the setup phase to generate the right number of clouds
     * @param number depends on the number of players in the game
     */
    public void placeCloudTiles(int number){
        for(int i = 0; i < number; i++) {
            clouds.add(new Cloud());
        }
    }

    /**
     * method used to move MotherNature after the player input
     * @param steps selected by the player
     */
    public void moveMotherNature(int steps){
        motherNaturePosition = (motherNaturePosition + steps) % islands.size();
    }

    /**
     * method used to remove an island from the table after a merge
     * @param island the island to delete
     */
    public void deleteIsland(Island island){
        islands.remove(island);
    }

    /**
     * @param tower the color of the tower
     * @return the number of towers placed of this color
     */
    public int countTowers(Tower tower){
        return islands.stream()
                .filter(Island::hasTower)
                .filter(i -> i.getTowerColor() == tower)
                .mapToInt(Island::getNumUnits)
                .sum();
    }

    /**
     * method able to unify adjacent islands with the same tower color
     * @param a the first island to merge
     * @param merging every other island needed to merge
     */
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

    /**
     * method to set the Characters for this game, only when Expert Mode is selected.
     * @param c the ArrayList of Character generated in setup
     */
    public void setCharacters(ArrayList<CharacterCard> c) {
        characters[0] = c.get(0);
        characters[1] = c.get(1);
        characters[2] = c.get(2);
    }

    /**
     *
     * @return the list of Characters in the game, only if Expert Mode is selected.
     */
    public CharacterCard[] getCharacters() {
        return characters;
    }

    public int getAdditionalMNMovements() {
        return additionalMNMovements;
    }

    /**
     * method used only when Character 4 effect is triggered
     * @param additionalMNMovements number of additional steps allowed for MotherNature
     */
    public void setAdditionalMNMovements(int additionalMNMovements) {
        this.additionalMNMovements = additionalMNMovements;
    }

    public boolean getDoNotCountTowers(){
        return doNotCountTowers;
    }

    /**
     * method used only when Character 6 effect is triggered
     */
    public void setDoNotCountTowers(boolean bool){
        doNotCountTowers=bool;}


    public Color getDoNotCountColor(){
        return doNotCountColor;
    }

    /**
     * method used only when Character 9 effect is triggered
     * @param color the color selected by the player
     */
    public void setDoNotCountColor(Color color){
        this.doNotCountColor=color;
    }

    public void resetDoNotCountColor(){
        this.doNotCountColor=null;
    }

    /**
     *
     * @return the list of the active characters
     */

    public CharacterCard getPlayedCharacter() {
        return playedCharacter;
    }

    public void setPlayedCharacter(CharacterCard c){
        this.playedCharacter=c;
    }
}
