package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.characters.CharacterCard;

import java.util.ArrayList;

public class UpdateBoard implements Message {
    private final ArrayList<CharacterCard> playedCharacters;
    private final Dashboard dashboard;

    public UpdateBoard(ArrayList<CharacterCard> playedCharacters, Dashboard dashboard) {
        this.playedCharacters = playedCharacters;
        this.dashboard = dashboard;
    }

    public ArrayList<CharacterCard> getPlayedCharacters() {
        return playedCharacters;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }
}
