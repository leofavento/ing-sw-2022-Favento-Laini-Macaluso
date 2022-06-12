package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.MessageReceiver;
import it.polimi.ingsw.client.gui.controllers.DashboardController;
import it.polimi.ingsw.client.gui.controllers.EndGameController;
import it.polimi.ingsw.client.gui.controllers.SetupController;
import it.polimi.ingsw.client.gui.controllers.initial.LobbyController;
import it.polimi.ingsw.client.gui.controllers.initial.WaitingPlayersController;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.Pong;
import it.polimi.ingsw.messages.fromServer.*;

public class GUIMessageReceiver implements MessageReceiver {
    private final GUI gui;

    public GUIMessageReceiver(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void receiveMessage(CommunicationMessage message) {
        if (message == CommunicationMessage.ENTER_NICKNAME) {
            gui.updateScene(FxmlScenes.NICKNAME.getPhase());
        } else if (message == CommunicationMessage.SUCCESS) {
            gui.getCurrentController().nextPhase();
        } else if (message == CommunicationMessage.HOST_LEFT) {
            gui.getCurrentController().error("The host left, you are being disconnected...");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gui.updateScene(FxmlScenes.CONNECTION.getPhase());
        }
    }

    @Override
    public void receiveMessage(ErrorMessage message) {
        gui.errorMessage(message.getMessage());
    }

    @Override
    public void receiveMessage(AvailableGames message) {
        if (gui.getController(FxmlScenes.LOBBY.getPhase()) instanceof LobbyController) {
            ((LobbyController) gui.getController(FxmlScenes.LOBBY.getPhase())).updateTable(message.getAvailableGames());
        }
    }

    @Override
    public void receiveMessage(JoinAlreadyExistingGame message) {
        gui.updateScene(FxmlScenes.WAITING.getPhase());
        ((WaitingPlayersController)gui.getController(FxmlScenes.WAITING.getPhase())).setGameInfo(message.getGameInfo());
    }

    @Override
    public void receiveMessage(WaitingForPlayers message) {
        gui.updateScene(FxmlScenes.WAITING.getPhase());
        ((WaitingPlayersController)gui.getController(FxmlScenes.WAITING.getPhase())).setMessage(message.getMessage());
    }

    @Override
    public void receiveMessage(UpdateLobby message) {
        gui.getView().setTotalPlayers(message.getGameInfo().getNumOfTotalPlayers());
        gui.getView().setActivePlayers(message.getGameInfo().getNumOfWaitingPlayers());
        gui.getView().setExpertMode(message.getGameInfo().isExpertGame());
        ((WaitingPlayersController)gui.getController(FxmlScenes.WAITING.getPhase())).update();
    }

    @Override
    public void receiveMessage(MatchStarted message) {
        gui.updateScene(FxmlScenes.SETUP.getPhase());
    }

    @Override
    public void receiveMessage(AvailableTowers message) {
        ((SetupController)gui.getController(FxmlScenes.SETUP.getPhase())).update(message);
    }

    @Override
    public void receiveMessage(AvailableWizards message) {
        ((SetupController)gui.getController(FxmlScenes.SETUP.getPhase())).update(message);
    }

    @Override
    public void receiveMessage(UpdateBoard message) {
        if (gui.getCurrentController() instanceof SetupController) {
            ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).setup(message.getPlayers());
            gui.updateScene(FxmlScenes.DASHBOARD.getPhase());
        }
        gui.getView().setDashboard(message.getDashboard());
        gui.getView().setPlayers(message.getPlayers());
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).update();
    }

    @Override
    public void receiveMessage(PlayerStatusMessage message) {
        switch(message.getPlayerStatus()) {
            case PLANNING -> {}
            case END_PLANNING -> {
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).resetError();
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).resetAssistants();
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).setInstruction("Other players are taking their turn, please wait");
            }
        }
        gui.getClient().sendMessage(new Ack());
    }

    @Override
    public void receiveMessage(AvailableAssistants message) {
        gui.getView().setAvailableAssistants(message.getAvailableAssistants());
        gui.getView().setPlayedAssistants(message.getPlayedAssistants());
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).requestAssistants();
    }

    @Override
    public void receiveMessage(PlayedAssistant message) {
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).getSchoolboardController(message.getPlayer()).showPlayed(message.getAssistant());
    }

    @Override
    public void receiveMessage(StartOfPlayerRound message) {

    }

    @Override
    public void receiveMessage(CommunicateWinner message) {
        ((EndGameController) gui.getController(FxmlScenes.END_GAME.getPhase())).update(message);
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
        gui.getCurrentController().error(message.getNickname() + " left, you are being disconnected...");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gui.updateScene(FxmlScenes.CONNECTION.getPhase());
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
