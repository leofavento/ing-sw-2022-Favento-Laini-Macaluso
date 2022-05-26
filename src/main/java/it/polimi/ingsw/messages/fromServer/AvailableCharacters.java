package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.characters.CharacterCard;

import java.util.ArrayList;

/**
 * Message sent by server to communicate the list of Characters available in the game (only in ExpertMode).
 */
public class AvailableCharacters implements FromServerMessage {
    private final ArrayList<CharacterCard> characters;

    public AvailableCharacters(ArrayList<CharacterCard> characters) {
        this.characters = characters;
    }

    public ArrayList<CharacterCard> getAvailableCharacters() {
        return characters;
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
