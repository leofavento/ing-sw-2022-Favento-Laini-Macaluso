package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.model.Island;

/**
 * this Character allows the player to place a No Entry Tile on a selected Island. The first time Mother Nature ends her movement on this Island remove the Tile and do not apply influence.
 */
public class Char5 extends CharacterCard {

    public Char5() {
        value = CharacterEnum.valueOf(("Char5"));
        this.cost = 2;
        this.noEntryTiles = 4;
    }

    @Override
    public void useNoEntryTile() throws NoEntryTilesLeftException {
        if (noEntryTiles == 0) {
            throw new NoEntryTilesLeftException("There are " + noEntryTiles + " NO ENTRY TILES left");
        } else {
            noEntryTiles--;
        }
    }

    @Override
    public void addNoEntryTile() {
        noEntryTiles++;
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
