package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.model.Island;

public class Char5 extends CharacterCard{

    private int noEntryTiles=4;

    public Char5(){
        this.cost=2;
    }


    public int getNoEntryTiles(){
        return noEntryTiles;
    }

    public void useNoEntryTiles(Island island) throws NoEntryTilesLeftException {
        if(noEntryTiles==0){
            throw new NoEntryTilesLeftException("There are " + noEntryTiles +  " NO ENTRY TILES left");
        }
        else{
            island.addNoEntry();
            noEntryTiles--;
        }
    }
}
