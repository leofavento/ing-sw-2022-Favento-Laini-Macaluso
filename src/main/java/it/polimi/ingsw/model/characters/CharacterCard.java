package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public abstract class CharacterCard {

    CharacterEnum value=null;
    int cost;
    boolean isActive=false;
    String UsedBy=null;
    ArrayList<Color> students;
    int noEntryTiles=0;
    Color color;

    public int getCost(){return cost;}

    public void increaseCost(){
        this.cost++;
    }

    public void setActive(){
        this.isActive=true;
    }

    public void setInactive(){
        this.isActive=false;
    }

    public boolean getActive(){return isActive;}

    public void setUsedBy(String nickname){
        this.UsedBy=nickname;
    }

    public void resetUsedBy(){this.UsedBy=null;}

    public String getUsedBy(){return UsedBy;}

    public CharacterEnum getValue(){return value;}

    public void setUp (Bag bag){}

    public void addStudent(Color c){}

    public ArrayList<Color> getStudents(){return students;}

    public void removeStudent(Color c){}

    public int getNoEntryTiles(){
        return noEntryTiles;
    }

    public void addNoEntryTile(){}

    public void useNoEntryTile() throws NoEntryTilesLeftException {}

    public Color getColor(){return color;}

    public void setColor(Color c){}

    public void resetColor(){}
}
