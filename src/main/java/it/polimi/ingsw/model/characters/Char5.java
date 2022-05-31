package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;

/**
 * this Character allows the player to place a No Entry Tile on a selected Island. The first time Mother Nature ends her movement on this Island remove the Tile and do not apply influence.
 */
public class Char5 extends CharacterCard {

    public Char5() {
        value = CharacterEnum.valueOf(("Char5"));
        this.cost = 2;
        this.noEntryTiles = 4;
        description = "Place a No Entry tile on an Island of your choice. The first time Mother Nature " +
                "ends her movement there, put the No Entry tile back onto this card. " +
                "DO NOT calculate influence on that Island, or place any Towers.";
    }

    @Override
    public void useNoEntryTile() throws NoEntryTilesLeftException {
        if (noEntryTiles == 0) {
            throw new NoEntryTilesLeftException("There are " + noEntryTiles + " NO ENTRY TILES left");
        } else {
            noEntryTiles--;
        }
    }

    public int getNoEntryTiles() {
        return noEntryTiles;
    }

    @Override
    public void addNoEntryTile() {
        noEntryTiles++;
    }

    @Override
    public void activate(CharacterController controller) {
        controller.activate(this);
    }
}
