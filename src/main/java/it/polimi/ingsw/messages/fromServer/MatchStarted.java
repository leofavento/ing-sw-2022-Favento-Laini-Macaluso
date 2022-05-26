package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.cli.MessageReceiver;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInfo;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * Message sent by server to all the players communicating the start of the game.
 */
public class MatchStarted implements FromServerMessage {
    private final ArrayList<Player> players;

    public MatchStarted(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getMessage() {
        return "The game is starting...";
    }

    @Override
    public void receiveMessage(MessageReceiver messageReceiver) {
        messageReceiver.receiveMessage(this);
    }
}
