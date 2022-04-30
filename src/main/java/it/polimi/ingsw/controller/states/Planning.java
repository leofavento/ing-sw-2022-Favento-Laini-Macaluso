package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.exceptions.AlreadyPlayedAssistant;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Planning implements State {

    @Override
    public State nextState() {
        // TODO
        return this;
    }

    @Override
    public void execute(Game game) {

        for (Cloud cloud : game.getDashboard().getClouds()) {
            switch (game.getNumberOfPlayers()) {
                case (2):
                case (4):
                    IntStream.range(0, 3)
                            .forEach(i -> cloud.addStudent(game.getDashboard().getBag().drawStudent()));
                    break;
                case (3):
                    IntStream.range(0, 4)
                            .forEach(i -> cloud.addStudent(game.getDashboard().getBag().drawStudent()));
                    break;
            }
        }

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

    private void playAssistant(ArrayList<Player> allPlayers, Player player, Assistant assistant) throws AlreadyPlayedAssistant {
        ArrayList<Assistant> realAvailableAssistants = new ArrayList<>(player.getAvailableAssistants());
        
        for (int i = 0; i < allPlayers.indexOf(player); i++) {
            realAvailableAssistants.remove(allPlayers.get(i).getPlayedAssistant());
        }

        if (! player.getAvailableAssistants().contains(assistant)) {
            throw new AlreadyPlayedAssistant();
        } else if (! realAvailableAssistants.contains(assistant)) {
            throw new AlreadyPlayedAssistant();
        } else {
            player.playAssistant(assistant);
        }
    }
}
