package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;

public class SetGame implements Message {
    private int NumberOfPlayers;
    private boolean ExpertMode;

    public SetGame(int NumberOfPlayers, boolean ExpertMode){
        this.NumberOfPlayers = NumberOfPlayers;
        this.ExpertMode = ExpertMode;
    }

    public int getNumberOfPlayers(){
        return NumberOfPlayers;
    }

    public boolean getExpertMode(){
        return ExpertMode;
    }

}