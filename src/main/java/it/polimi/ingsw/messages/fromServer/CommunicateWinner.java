package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.controller.EndOfGameReason;
import it.polimi.ingsw.model.Tower;

import java.util.ArrayList;

/**
 * Message sent by the server to all the clients to communicate the winner of the game.
 */
public class CommunicateWinner implements FromServerMessage {
    private final Tower team;
    private final ArrayList<String> nicknames;
    private final EndOfGameReason winReason;

    public CommunicateWinner(Tower team, ArrayList<String> nicknames, EndOfGameReason winReason){
        this.team = team;
        this.nicknames = nicknames;
        this.winReason = winReason;
    }

    public Tower getTeam() {
        return team;
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public EndOfGameReason getWinReason() {
        return winReason;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
