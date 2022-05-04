package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.Message;

public class WaitingForPlayers implements Message {

    public WaitingForPlayers(){}

    public String getMessage(){
        return "Waiting for other players to join the game";
    }
}
