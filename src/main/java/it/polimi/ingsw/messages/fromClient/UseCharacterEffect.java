package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.characters.CharacterCard;

public class UseCharacterEffect implements Message {
    private final CharacterCard character;

    public UseCharacterEffect(CharacterCard character) {
        this.character = character;
    }

    public CharacterCard getCharacter() {
        return character;
    }
}
