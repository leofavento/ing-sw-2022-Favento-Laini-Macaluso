package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.componentRenderer.PlayersOrderRenderer;
import it.polimi.ingsw.client.cli.gameStates.*;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.Pong;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.player.PlayerStatus;

import java.util.Objects;

public class MessageReceiver {
    private final CLI cli;
    private final Client client;

    public MessageReceiver(CLI cli, Client client) {
        this.cli = cli;
        this.client = client;
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
            new Thread(new StateManager(cli, new NicknameState(cli))).start();
        } else if (message == CommunicationMessage.STUDENT_MOVED) {
            cli.getView().setMovableStudents(null);
            synchronized (cli.getGameState()) {
                cli.getGameState().notifyAll();
            }
        }
    }

    public void receiveMessage(ErrorMessage message) {
        cli.getView().setLastErrorMessage(message);

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
        cli.getView().setActivePlayers(message.getGameInfo().getNumOfWaitingPlayers());
        cli.getView().setTotalPlayers(message.getGameInfo().getNumOfTotalPlayers());
        cli.getView().setExpertMode(message.getGameInfo().isExpertGame());
        cli.getView().setHost(false);

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(MatchStarted message) {
        System.out.println(message.getMessage());
        cli.getView().setPlayers(message.getPlayers());
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
        new Thread(new StateManager(cli, new GameSetupState(cli))).start();
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
        cli.getClient().sendMessage(new Ack());
    }

    public void receiveMessage(PlayerStatusMessage message) {
        PlayerStatus playerStatus= message.getPlayerStatus();

        System.out.println(message.getPlayerStatus());
        if (playerStatus == PlayerStatus.WAITING) {
            new Thread(new StateManager(cli, new WaitingState(cli))).start();
        } else if (playerStatus == PlayerStatus.PLANNING) {
            new Thread(new StateManager(cli, new PlanningState(cli))).start();
        } else if (playerStatus == PlayerStatus.END_PLANNING) {
            System.out.println("You have completed your Planning phase decisions. Please wait a moment...");
            cli.getClient().sendMessage(new Ack());
        } else if (playerStatus == PlayerStatus.MOVE_1) {
            new Thread(new StateManager(cli, new ActionStep1State(cli))).start();
        } else if (playerStatus == PlayerStatus.END_MOVE_1) {
            synchronized (cli.getGameState()) {
                cli.getGameState().notifyAll();
            }
        } else if (playerStatus == PlayerStatus.MOVE_2) {
            new Thread(new StateManager(cli, new ActionStep2State(cli))).start();
        } else if (playerStatus == PlayerStatus.END_MOVE_2) {
            cli.setSuccess(true);
            synchronized (cli.getGameState()) {
                cli.getGameState().notifyAll();
            }
        } else if (playerStatus == PlayerStatus.MOVE_3) {
            new Thread(new StateManager(cli, new ActionStep3State(cli))).start();
        }
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
        cli.getView().setCurrentPlayer(message.getNickname());
        System.out.println("Starting round number " + message.getRoundNumber() + ".");
        System.out.println("Players order for this round:");
        PlayersOrderRenderer.playersOrderRenderer(cli.getView().getPlayers());
        if (Objects.equals(message.getNickname(), cli.getClient().getNickname())) {
            System.out.println("It's your turn!");
        } else {
            System.out.println("It is " + message.getNickname() + "'s turn.");
        }
    }

    public void receiveMessage(CommunicateWinner message) {
        cli.getView().setWinnerTeam(message.getTeam());
        cli.getView().setEndOfGameReason(message.getWinReason());
        cli.getView().setWinners(message.getNicknames());
        new Thread(new EndOfGameState(cli)).start();
    }

    public void receiveMessage(EndOfPlayerRound message) {
        if (Objects.equals(message.getNickname(), cli.getClient().getNickname())) {
            System.out.println("Your round finished.");
            cli.setSuccess(true);
            synchronized (cli.getGameState()) {
                notifyAll();
            }
        } else {
            System.out.println("The round of " + message.getNickname() + " finished.");
        }
        cli.getClient().sendMessage(new Ack());
    }

    public void receiveMessage(EndOfRound message) {
        System.out.printf("End of the %d%s round.%n",
                message.getRoundNumber(),
                message.getRoundNumber() % 10 == 1 ? "st" : message.getRoundNumber() % 10 == 2 ? "nd" : "th");
    }

    public void receiveMessage(IslandOwner message) {
        System.out.println(message.getNickname() + " now owns this island!");
        cli.getClient().sendMessage(new Ack());
    }

    public void receiveMessage(MotherNatureSteps message) {
        cli.getView().setMotherNatureSteps(message.getMaxStepsAllowed());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(MovableStudents message) {
        cli.getView().setMovableStudents(message.getStudents());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(PlayerDisconnected message) {
        System.out.println(message.getNickname() + " disconnected. The server is closing your game...");
    }

    public void receiveMessage(SelectCloud message) {
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(SelectColor message) {
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(WhereToMove message) {
        cli.getView().setRequiredDestination(true);
        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }

    public void receiveMessage(PlayedCharacter message) {
        if (Objects.equals(message.getPlayer(), cli.getClient().getNickname())) {
            cli.setSuccess(true);
            synchronized (cli.getGameState()) {
                cli.getGameState().notifyAll();
            }
        } else {
            System.out.println(message.getPlayer() + " activated " + message.getCharacterEnum() + "!");
        }
    }

    public void receiveMessage(MovableStudentsChar message) {
        cli.getView().setMovableStudentsChar(message.getStudents());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }

    }

    public void receiveMessage(Ping message) {
        cli.getClient().sendMessage(new Pong());
    }
}
