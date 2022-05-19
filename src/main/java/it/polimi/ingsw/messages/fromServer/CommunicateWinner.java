package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.controller.EndOfGameReason;
import it.polimi.ingsw.messages.Message;

import java.util.ArrayList;

public class CommunicateWinner implements Message {
    private final ArrayList<String> nicknames;
    private final EndOfGameReason winReason;

    public CommunicateWinner(ArrayList<String> nicknames, EndOfGameReason winReason){
        this.nicknames = nicknames;
        this.winReason = winReason;
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public EndOfGameReason getWinReason() {
        return winReason;
    }
}
