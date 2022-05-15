package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Action;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistant;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.PlayerStatusMessage;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planning implements State {
    Game game;
    Map<Player, Assistant> playedAssistants = new HashMap<>();

    private final List<Observer<Message>> observers = new ArrayList<>();

    @Override
    public State nextState() {
        // TODO
        return null;
    }

    @Override
    public void execute(Game game) {
        this.game = game;

        initializeStudentsToClouds();

        for (Player player : game.getOnlinePlayers()) {
            player.setStatus(PlayerStatus.WAITING);
        }
        game.getCurrentPlayer().setStatus(PlayerStatus.PLANNING);
        notifyStatus();

        for (Player player : game.getOnlinePlayers()) {
            game.setCurrentPlayer(player);

            // TODO ask current player to select assistant
            Assistant receivedAssistant = null;
            try {
                playAssistant(game.getOnlinePlayers(), player, receivedAssistant);
            } catch (AlreadyPlayedAssistant e) {
                // TODO ask again to select an assistant
            }
        }
    }

    @Override
    public void receiveMessage(Message message, String sender) {

    }

    private void playAssistant(ArrayList<Player> allPlayers, Player player, Assistant assistant) throws AlreadyPlayedAssistant {
        ArrayList<Assistant> realAvailableAssistants = new ArrayList<>(player.getAvailableAssistants());
        
        for (int i = 0; i < allPlayers.indexOf(player); i++) {
            realAvailableAssistants.remove(allPlayers.get(i).getPlayedAssistant());
        }

        if (! player.getAvailableAssistants().contains(assistant)) {
            throw new AlreadyPlayedAssistant("This assistant has already been played in a previous turn.");
        } else if (! realAvailableAssistants.contains(assistant)) {
            throw new AlreadyPlayedAssistant("This assistant has already been played by another player.");
        } else {
            player.playAssistant(assistant);
        }
    }

    private void initializeStudentsToClouds() {
        for (Cloud cloud : game.getDashboard().getClouds()) {
            switch (game.getNumberOfPlayers()) {
                case (2):
                case (4):
                    Action.moveFromBagToDeposit(game.getDashboard().getBag(),
                            cloud,
                            3);
                    break;
                case (3):
                    Action.moveFromBagToDeposit(game.getDashboard().getBag(),
                            cloud,
                            4);
                    break;
            }
        }
    }

    private void notifyStatus() {
        for (Player player : game.getOnlinePlayers()) {
            notify(new PlayerStatusMessage(player.getStatus()));
        }
    }

    @Override
    public void addObserver(Observer<Message> observer) {
        observers.add(observer);
    }

    @Override
    public void notify(Message message) {
        for(Observer<Message> o : observers) {
            o.update(message);
        }
    }
}
