package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.messages.fromClient.Pong;
import it.polimi.ingsw.messages.fromServer.*;

public class GUIMessageReceiver implements MessageReceiver {
    private GUI gui;

    public GUIMessageReceiver(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void receiveMessage(CommunicationMessage message) {

    }

    @Override
    public void receiveMessage(ErrorMessage message) {

    }

    @Override
    public void receiveMessage(AvailableGames message) {

    }

    @Override
    public void receiveMessage(JoinAlreadyExistingGame message) {

    }

    @Override
    public void receiveMessage(WaitingForPlayers message) {

    }

    @Override
    public void receiveMessage(UpdateLobby message) {

    }

    @Override
    public void receiveMessage(MatchStarted message) {

    }

    @Override
    public void receiveMessage(AvailableTowers message) {

    }

    @Override
    public void receiveMessage(AvailableWizards message) {

    }

    @Override
    public void receiveMessage(UpdateBoard message) {

    }

    @Override
    public void receiveMessage(PlayerStatusMessage message) {

    }

    @Override
    public void receiveMessage(AvailableAssistants message) {

    }

    @Override
    public void receiveMessage(PlayedAssistant message) {

    }

    @Override
    public void receiveMessage(StartOfPlayerRound message) {

    }

    @Override
    public void receiveMessage(CommunicateWinner message) {

    }

    @Override
    public void receiveMessage(EndOfPlayerRound message) {

    }

    @Override
    public void receiveMessage(EndOfRound message) {

    }

    @Override
    public void receiveMessage(IslandOwner message) {

    }

    @Override
    public void receiveMessage(MotherNatureSteps message) {

    }

    @Override
    public void receiveMessage(MovableStudents message) {

    }

    @Override
    public void receiveMessage(PlayerDisconnected message) {

    }

    @Override
    public void receiveMessage(SelectCloud message) {

    }

    @Override
    public void receiveMessage(SelectColor message) {

    }

    @Override
    public void receiveMessage(WhereToMove message) {

    }

    @Override
    public void receiveMessage(PlayedCharacter message) {

    }

    @Override
    public void receiveMessage(MovableStudentsChar message) {

    }

    @Override
    public void receiveMessage(Ping message) {
        gui.getClient().sendMessage(new Pong());
    }
}
