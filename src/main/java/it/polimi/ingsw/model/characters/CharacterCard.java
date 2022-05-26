package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class CharacterCard implements Serializable {

    CharacterEnum value = null;
    int cost;
    boolean isActive = false;
    String UsedBy = null;
    ArrayList<Color> students;
    int noEntryTiles = 0;

    /**
     *
     * @return the number of coins required to activate effect
     */
    public int getCost() {
        return cost;
    }

    /**
     * method used to increase the cost of activation, after a player has trigger the effect
     */
    public void increaseCost() {
        this.cost++;
    }

    public void setActive() {
        this.isActive = true;
    }

    public void setInactive() {
        this.isActive = false;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setUsedBy(String nickname) {
        this.UsedBy = nickname;
    }

    public void resetUsedBy() {
        this.UsedBy = null;
    }

    public String getUsedBy() {
        return UsedBy;
    }

    /**
     *
     * @return the type of the Character, contained in the enum list
     */
    public CharacterEnum getValue() {
        return value;
    }

    /**
     * method used to fill the Character with students (only in Character 1, 7 and 11)
     * @param bag the current bag
     */
    public void setUp(Bag bag) {
    }

    public void addStudent(Color c) {
    }

    public ArrayList<Color> getStudents() {
        return students;
    }

    public void removeStudent(Color c) {
    }

    /**
     * method used to add back the No Entry Tile on the Character (only in Character 5)
     */
    public void addNoEntryTile() {
    }

    public void useNoEntryTile() throws NoEntryTilesLeftException {
    }


    /**
     * method used to trigger the Character effect
     * @param controller the Character controller
     */
    public abstract void activate(CharacterController controller);
}
