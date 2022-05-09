package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;

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

    public void setUsedBy(String Player){
        this.UsedBy=Player;
    }

    public void resetUsedBy(){this.UsedBy=null;}

    public String getUsedBy(){return UsedBy;}

    public CharacterEnum getValue(){return value;}

    public void setUp (Bag bag){}

    void addStudent(Color c){}

    ArrayList<Color> getStudents(){return students;}

    void removeStudent(Color c){}

    int getNoEntryTiles(){
        return noEntryTiles;
    }

    void useNoEntryTiles(Island island) throws NoEntryTilesLeftException {}

    Color getColor(){return color;}

    void setColor(Color c){}

    void resetColor(){}
}
