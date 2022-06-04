package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.fromServer.*;

public interface MessageReceiver {
    void receiveMessage(CommunicationMessage message);
    void receiveMessage(ErrorMessage message);
    void receiveMessage(AvailableGames message);
    void receiveMessage(JoinAlreadyExistingGame message);
    void receiveMessage(WaitingForPlayers message);
    void receiveMessage(UpdateLobby message);
    void receiveMessage(MatchStarted message);
    void receiveMessage(AvailableTowers message);
    void receiveMessage(AvailableWizards message);
    void receiveMessage(UpdateBoard message);
    void receiveMessage(PlayerStatusMessage message);
    void receiveMessage(AvailableAssistants message);
    void receiveMessage(PlayedAssistant message);
    void receiveMessage(StartOfPlayerRound message);
    void receiveMessage(CommunicateWinner message);
    void receiveMessage(EndOfPlayerRound message);
    void receiveMessage(EndOfRound message);
    void receiveMessage(IslandOwner message);
    void receiveMessage(MotherNatureSteps message);
    void receiveMessage(MovableStudents message);
    void receiveMessage(PlayerDisconnected message);
    void receiveMessage(SelectCloud message);
    void receiveMessage(SelectColor message);
    void receiveMessage(WhereToMove message);
    void receiveMessage(PlayedCharacter message);
    void receiveMessage(MovableStudentsChar message);
    void receiveMessage(Ping message);
}
