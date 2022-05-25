package it.polimi.ingsw.client.cli.componentRenderer;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class PlayersOrderRenderer {

    /**
     * This method prints to command line the order in which the players will play their turn
     * @param players list of the players
     */
    public static void playersOrderRenderer(ArrayList<Player> players) {
        for (Player player : players) {
            System.out.printf("%d) %s%n",
                    players.indexOf(player) + 1,
                    player.getNickname());
        }
    }
}
