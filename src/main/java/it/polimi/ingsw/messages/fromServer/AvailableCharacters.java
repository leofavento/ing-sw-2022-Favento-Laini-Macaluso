package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.characters.CharacterCard;

import java.util.ArrayList;

public class AvailableCharacters implements Message {
    private final ArrayList<CharacterCard> characters;

    public AvailableCharacters(ArrayList<CharacterCard> characters) {
        this.characters = characters;
    }

    public ArrayList<CharacterCard> getAvailableCharacters() {
        return characters;
    }
}
