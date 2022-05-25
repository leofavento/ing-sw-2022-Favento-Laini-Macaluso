package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.componentRenderer.CloudsRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.IslandsRenderer;
import it.polimi.ingsw.client.cli.componentRenderer.SchoolBoardRenderer;
import it.polimi.ingsw.client.cli.gameStates.*;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;

import java.util.HashMap;

public class MessageReceiver {
    private final CLI cli;
    private final Client client;

    public MessageReceiver(CLI cli, Client client) {
        this.cli = cli;
        this.client = client;
    }

    public void receiveMessage(Message message) {
        if (message instanceof CommunicationMessage) {
            receiveMessage((CommunicationMessage) message);
        } else if (message instanceof ErrorMessage) {
            receiveMessage((ErrorMessage) message);
        } else if (message instanceof AvailableGames) {
            receiveMessage((AvailableGames) message);
        } else if (message instanceof JoinAlreadyExistingGame) {
            receiveMessage((JoinAlreadyExistingGame) message);
        } else if (message instanceof WaitingForPlayers) {
            receiveMessage((WaitingForPlayers) message);
        } else if (message instanceof UpdateLobby) {
            receiveMessage((UpdateLobby) message);
        } else if (message instanceof NewGame) {
            receiveMessage((NewGame) message);
        } else if (message instanceof AvailableTowers) {
            receiveMessage((AvailableTowers) message);
        } else if (message instanceof AvailableWizards) {
            receiveMessage((AvailableWizards) message);
        } else if (message instanceof UpdateBoard) {
            receiveMessage((UpdateBoard) message);
        } else if (message instanceof PlayerStatusMessage) {
            receiveMessage((PlayerStatusMessage) message);
        } else if (message instanceof AvailableAssistants) {
            receiveMessage((AvailableAssistants) message);
        } else if (message instanceof PlayedAssistant) {
            receiveMessage((PlayedAssistant) message);
        } else if (message instanceof StartOfPlayerRound) {
            receiveMessage((StartOfPlayerRound) message);
        } else {
            System.out.println("THIS MESSAGE WAS IGNORED:" + message);
        }
    }

    public void receiveMessage(CommunicationMessage message) {
        if (message != CommunicationMessage.SUCCESS) {
            System.out.println(message.getMessage());
        }

        if (message == CommunicationMessage.SUCCESS) {
            cli.setSuccess(true);
            synchronized (cli.getGameState()) {
                cli.getGameState().notifyAll();
            }
        } else if (message == CommunicationMessage.ENTER_NICKNAME) {
            cli.setGameState(new NicknameState(cli));
        }
    }

    public void receiveMessage(ErrorMessage message) {
        System.out.println(message.getMessage());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(AvailableGames message) {
        cli.setAvailableGames(message.getAvailableGames());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(JoinAlreadyExistingGame message) {
        cli.getView().setActivePlayers(message.getGameInfo().getNumOfWaitingPlayers());
        cli.getView().setTotalPlayers(message.getGameInfo().getNumOfTotalPlayers());
        cli.getView().setExpertMode(message.getGameInfo().isExpertGame());
        cli.getView().setHost(false);
        cli.setSuccess(true);
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(WaitingForPlayers message) {
        System.out.println(message.getMessage());
        cli.setSuccess(true);
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(UpdateLobby message) {
        int remaining = message.getGameInfo().getNumOfTotalPlayers() - message.getGameInfo().getNumOfWaitingPlayers();

        cli.getView().setActivePlayers(message.getGameInfo().getNumOfWaitingPlayers());
        cli.getView().setTotalPlayers(message.getGameInfo().getNumOfTotalPlayers());
        cli.getView().setExpertMode(message.getGameInfo().isExpertGame());
        cli.getView().setHost(false);

        if (remaining == 0){
            cli.setSuccess(true);
        }
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(NewGame message) {
        System.out.println(message.getMessage());
        cli.getView().setPlayers(message.getPlayers());
        cli.setGameState(new GameSetupState(cli));
    }

    public void receiveMessage(AvailableTowers message) {
        cli.getView().setAvailableTowers(message.getAvailableTowers());
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(AvailableWizards message) {
        cli.getView().setAvailableWizards(message.getAvailableWizards());
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(UpdateBoard message) {
        cli.getView().setDashboard(message.getDashboard());
        cli.getView().setPlayers(message.getPlayers());
        CloudsRenderer.cloudRenderer(cli.getView().getDashboard());
        IslandsRenderer.islandsRenderer(cli.getView().getDashboard());
        for (Player player : cli.getView().getPlayers()) {
            SchoolBoardRenderer.renderSchoolBoard(player);
        }
        cli.getClient().sendMessage(new Ack());
    }

    public void receiveMessage(PlayerStatusMessage message) {
        PlayerStatus playerStatus= message.getPlayerStatus();

        if (playerStatus == PlayerStatus.WAITING) {
            cli.setGameState(new WaitingState(cli));
        } else if (playerStatus == PlayerStatus.PLANNING) {
            cli.setGameState(new PlanningState(cli));
        } else if (playerStatus == PlayerStatus.MOVE_1) {
            cli.setGameState(new ActionStep1State(cli));
        }
        cli.getClient().sendMessage(new Ack());
    }

    public void receiveMessage(AvailableAssistants message) {
        cli.getView().setAvailableAssistants(message.getAvailableAssistants());
        cli.getView().setPlayedAssistants(message.getPlayedAssistants());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(PlayedAssistant message) {
        System.out.printf("%s played %s (%d value, %d movement)%n",
                message.getPlayer(),
                message.getAssistant(),
                message.getAssistant().getValue(),
                message.getAssistant().getMovements());
    }

    public void receiveMessage(StartOfPlayerRound message) {
        cli.getView().setRoundNumber(message.getRoundNumber());
    }
}
