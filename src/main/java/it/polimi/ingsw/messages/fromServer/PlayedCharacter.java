package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.model.characters.CharacterEnum;

/**
 * Message sent by the server to all the players, containing the name of the activated character and the active player.
 */
public class PlayedCharacter implements FromServerMessage {
    private final CharacterEnum characterEnum;
    private final String player;

    public PlayedCharacter(CharacterEnum characterEnum, String player) {
        this.characterEnum = characterEnum;
        this.player = player;
    }

    public CharacterEnum getCharacterEnum() {
        return characterEnum;
    }

    public String getPlayer() {
        return player;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
