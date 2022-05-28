package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.characters.CharacterEnum;

/**
 * Message sent by client to communicate the intention to trigger the effect of a selected character.
 */

public class UseCharacterEffect implements Message {
    private final CharacterEnum character;

    public UseCharacterEffect(CharacterEnum character) {
        this.character = character;
    }

    public CharacterEnum getCharacter() {
        return character;
    }
}
