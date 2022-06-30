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
import javafx.application.Platform;

/**
 * class used to handle the communication between server and clients in GUI mode
 */
public class GUIMessageReceiver implements MessageReceiver {
    private final GUI gui;

    public GUIMessageReceiver(GUI gui) {
        this.gui = gui;
    }

    /**
     * method used to handle the case when a CommunicationMessage is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(CommunicationMessage message) {
        if (message == CommunicationMessage.ENTER_NICKNAME) {
            gui.updateScene(FxmlScenes.NICKNAME.getPhase());
        } else if (message == CommunicationMessage.SUCCESS) {
            gui.getView().setActivatedCharacterEffect(false);
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

    /**
     * method used to handle the case when an ErrorMessage is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(ErrorMessage message) {
        gui.errorMessage(message.getMessage());
    }

    /**
     * method used to handle the case when an AvailableGames message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(AvailableGames message) {
        if (gui.getController(FxmlScenes.LOBBY.getPhase()) instanceof LobbyController) {
            ((LobbyController) gui.getController(FxmlScenes.LOBBY.getPhase())).updateTable(message.getAvailableGames());
        }
    }

    /**
     * method used to handle the case when a JoinAlreadyExistingGame message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(JoinAlreadyExistingGame message) {
        gui.updateScene(FxmlScenes.WAITING.getPhase());
        ((WaitingPlayersController) gui.getController(FxmlScenes.WAITING.getPhase())).setGameInfo(message.getGameInfo());
    }

    /**
     * method used to handle the case when a WaitingForPlayers message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(WaitingForPlayers message) {
        gui.updateScene(FxmlScenes.WAITING.getPhase());
        ((WaitingPlayersController) gui.getController(FxmlScenes.WAITING.getPhase())).setMessage(message.getMessage());
    }

    /**
     * method used to handle the case when an UpdateLobby message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(UpdateLobby message) {
        gui.getView().setTotalPlayers(message.getGameInfo().getNumOfTotalPlayers());
        gui.getView().setActivePlayers(message.getGameInfo().getNumOfWaitingPlayers());
        gui.getView().setExpertMode(message.getGameInfo().isExpertGame());
        ((WaitingPlayersController) gui.getController(FxmlScenes.WAITING.getPhase())).update();
    }

    /**
     * method used to handle the case when a MatchStarted message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(MatchStarted message) {
        gui.updateScene(FxmlScenes.SETUP.getPhase());
    }

    /**
     * method used to handle the case when an AvailableTowers message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(AvailableTowers message) {
        ((SetupController) gui.getController(FxmlScenes.SETUP.getPhase())).update(message);
    }

    /**
     * method used to handle the case when an AvailableWizards message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(AvailableWizards message) {
        ((SetupController) gui.getController(FxmlScenes.SETUP.getPhase())).update(message);
    }

    /**
     * method used to handle the case when an UpdateBoard message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(UpdateBoard message) {
        if (gui.getCurrentController() instanceof SetupController) {
            ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).setup(message.getPlayers());
            gui.updateScene(FxmlScenes.DASHBOARD.getPhase());
        }
        gui.getView().setDashboard(message.getDashboard());
        gui.getView().setPlayers(message.getPlayers());
        Platform.runLater(() -> ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).update());
        if (gui.getView().isExpertMode() && gui.getView().getDashboard().getPlayedCharacter() != null) {
            ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).disableCharactersButton(true);
        }
    }

    /**
     * method used to handle the case when a PlayerStatus message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(PlayerStatusMessage message) {
        switch (message.getPlayerStatus()) {
            case WAITING -> {
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).setInstruction("Other players are taking their turn, please wait");
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).cleanRequest();
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).resetError();
            }
            case PLANNING -> {
            }
            case END_PLANNING -> {
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).resetError();
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).resetAssistants();
                ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).setInstruction("Other players are taking their turn, please wait");
            }
        }
        gui.getClient().sendMessage(new Ack());
    }

    /**
     * method used to handle the case when an AvailableAssistants message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(AvailableAssistants message) {
        gui.getView().setAvailableAssistants(message.getAvailableAssistants());
        gui.getView().setPlayedAssistants(message.getPlayedAssistants());
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).requestAssistants();
    }

    /**
     * method used to handle the case when a PlayedAssistants message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(PlayedAssistant message) {
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).getSchoolboardController(message.getPlayer()).showPlayed(message.getAssistant());
    }

    /**
     * method used to handle the case when a StartOfPlayerRound message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(StartOfPlayerRound message) {
        gui.getView().setRoundNumber(message.getRoundNumber());
        gui.getView().setCurrentPlayer(message.getNickname());
    }

    /**
     * method used to handle the case when a CommunicateWinner message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(CommunicateWinner message) {
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).setEndGame(message);
    }

    /**
     * method used to handle the case when an EndOfPlayerRound message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(EndOfPlayerRound message) {

    }

    /**
     * method used to handle the case when an EndOfRound message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(EndOfRound message) {
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).removePlayedAssistants();
    }

    /**
     * method used to handle the case when an IslandOwner message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(IslandOwner message) {
        gui.getClient().sendMessage(new Ack());
    }

    /**
     * method used to handle the case when a MotherNatureSteps message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(MotherNatureSteps message) {
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).updateMotherNatureSteps(message.getMaxStepsAllowed());
        if (gui.getView().isExpertMode() && gui.getView().getDashboard().getPlayedCharacter() == null) {
            ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).disableCharactersButton(false);
        }
    }

    /**
     * method used to handle the case when a MovableStudents message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(MovableStudents message) {
        gui.getView().setMovableStudents(message.getStudents());
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).updateMovableStudents(message.getStudents());
        if (gui.getView().isExpertMode() && gui.getView().getDashboard().getPlayedCharacter() == null) {
            ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).disableCharactersButton(false);
        }
    }

    /**
     * method used to handle the case when a PlayerDisconnected message is received
     * @param message the received message
     */
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

    /**
     * method used to handle the case when a SelectCloud message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(SelectCloud message) {
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).updateCloudSelection(gui.getView().getTotalPlayers());
        if (gui.getView().isExpertMode() && gui.getView().getDashboard().getPlayedCharacter() == null) {
            ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).disableCharactersButton(false);
        }
    }

    /**
     * method used to handle the case when a SelectColor message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(SelectColor message) {
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).updateMovableStudents(gui.getView().getColors());
    }

    /**
     * method used to handle the case when a WhereToMove message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(WhereToMove message) {
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).requestDestination();
        if (gui.getView().isExpertMode()) {
            ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).disableCharactersButton(true);
        }
    }

    /**
     * method used to handle the case when a PlayedCharacter message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(PlayedCharacter message) {
        if (gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal() == 0 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal() == 2 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal() == 4 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal() == 6 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal() == 8 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal() == 9 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal() == 10 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal() == 11) {
            gui.getView().setActivatedCharacterEffect(true);
        }
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).disableCharactersButton(true);
    }

    /**
     * method used to handle the case when a MovableStudentsChar message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(MovableStudentsChar message) {
        gui.getView().setMovableStudentsChar(message.getStudents());
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).updateMovableStudents(message.getStudents());
    }

    /**
     * method used to handle the case when a Ping message is received
     * @param message the received message
     */
    @Override
    public void receiveMessage(Ping message) {
        gui.getClient().sendMessage(new Pong());
    }
}
