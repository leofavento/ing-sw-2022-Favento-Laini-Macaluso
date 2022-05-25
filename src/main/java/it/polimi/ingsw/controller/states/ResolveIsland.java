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

    @Override
    public void nextState() {
        controller.setState(previousState);
        previousState.resume();
    }

    @Override
    public void execute() {
        HashMap<Tower, Integer> teamsInfluence = new HashMap<>();

        for (Tower tower : game.getTeams()) {
            teamsInfluence.put(tower, island.countInfluence(game.getTeamFromTower(tower),
                    game.getDashboard().getDoNotCountTowers(), // true if Char6 is active
                    game.getDashboard().getDoNotCountColor())); // color determined by Char9
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

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        }
    }

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
