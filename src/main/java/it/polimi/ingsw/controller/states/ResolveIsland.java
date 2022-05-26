package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.messages.fromServer.IslandOwner;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * State created to calculate influence on an Island and to set up the Tower on it.
 *
 */
public class ResolveIsland implements State {
    Game game;
    Controller controller;
    ResumableState previousState;
    Island island;
    boolean requestedAck = false;
    boolean verifiedMerge = false;
    ArrayList<String> missingAcks = new ArrayList<>();

    public ResolveIsland(Game game, Controller controller, Island island, ResumableState previousState) {
        this.game = game;
        this.controller = controller;
        this.island = island;
        this.previousState = previousState;
    }

    /**
     * method used to change state to the previous one interrupted for resolving the Island
     */
    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    /**
     * method used to calculate the influence of each team on the Island.
     * It calls the countInfluence method in the Island class for every team. Then the results are filled
     * in the HashMap.
     * If the HashMap has only a max value, the corresponding Tower is the dominating one.
     * Then the method checks if the Island has already a tower placed on it, and proceed to set up the new one.
     * In case of new Tower, the server sends the updated board to all the players and checks if a Player or
     * Team has won the game.
     * In case of no changes, the server communicates that to all the players.
     *
     */
    @Override
    public void execute() {
        HashMap<Tower, Integer> teamsInfluence = new HashMap<>();

        for (Tower tower : game.getTeams()) {
            teamsInfluence.put(tower, island.countInfluence(game.getTeamFromTower(tower),
                    game.getDashboard().getDoNotCountTowers(), //true if Char6 effect is active
                    game.getDashboard().getDoNotCountColor())); // not null only if Char9 effect is active
        }

        Integer max = Collections.max(teamsInfluence.values());
        List<Tower> maxTowers;
        maxTowers = teamsInfluence.entrySet().stream()
                .filter(Tower -> Objects.equals(Tower.getValue(), max))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (maxTowers.size() == 1) {
            if (island.getTowerColor() != maxTowers.get(0)) {
                //if Island has already a tower
                if (island.hasTower()) {
                    //put existing tower in team SchoolBoard
                    game.getTeamFromTower(island.getTowerColor()).get(0).getSchoolBoard().addTower();
                }
                //add new tower
                island.setTowers(maxTowers.get(0));
                //remove new tower from the right team SchoolBoard
                game.getTeamFromTower(maxTowers.get(0)).get(0).getSchoolBoard().removeTower();
                missingAcks.addAll(game.getOnlinePlayers().stream()
                        .map(Player::getNickname)
                        .collect(Collectors.toList()));
                requestedAck = true;
                controller.notify(new IslandOwner(island, game.getTeamFromTower(maxTowers.get(0)).get(0).getNickname()));
            }
            controller.check();
        } else {
            controller.notify(CommunicationMessage.NO_CHANGES);
            nextState();
        }
    }

    /**
     * method used to check for merge the Island with the adjacent one.
     * The method checks if the Tower in the previous or the successive Island has the same color
     * comparing with the Tower on the just resolved Island.
     * If this happens, the method proceeds to unify the Islands.
     * @param island the just resolved Island
     */
    private void checkMerge(Island island) {
        int islandIndex = game.getDashboard().getIslands().indexOf(island);
        int previousIsland = (islandIndex - 1 >= 0)
                ? (islandIndex - 1)
                : (islandIndex - 1 + game.getDashboard().getIslands().size());
        int nextIsland = (islandIndex + 1 < game.getDashboard().getIslands().size())
                ? (islandIndex + 1)
                : (islandIndex + 1 - game.getDashboard().getIslands().size());

        ArrayList<Island> mergingIslands = new ArrayList<>();
        if (game.getDashboard().getIslands().get(previousIsland).getTowerColor() == island.getTowerColor()) {
            mergingIslands.add(game.getDashboard().getIslands().get(previousIsland));
        }
        if (game.getDashboard().getIslands().get(nextIsland).getTowerColor() == island.getTowerColor()) {
            mergingIslands.add(game.getDashboard().getIslands().get(nextIsland));
        }

        if (mergingIslands.size() > 0) {
            game.getDashboard().mergeIslands(island, mergingIslands.toArray(Island[]::new));
            missingAcks.addAll(game.getOnlinePlayers().stream()
                    .map(Player::getNickname)
                    .collect(Collectors.toList()));
            requestedAck = true;
            controller.notify(CommunicationMessage.UNIFIED_ISLANDS);
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
        } else {
            nextState();
        }
        verifiedMerge = true;
    }

    /**
     * method used to distinguish the received message and to trigger the right action.
     * @param message the message received
     * @param sender the sender nickname
     */
    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        }
    }

    /**
     * method used to check for the acknowledgment message from the players.
     * The first player acknowledgment is received after resolving the Island.
     * The second is received after the mergeIslands method.
     * In the end the method checks if a Player or a Team has won the game.
     * @param sender the sender nickname
     */
    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            requestedAck = false;
            if (! verifiedMerge) {
                checkMerge(island);
            } else {
                controller.check();
                nextState();
            }
        }
    }
}
