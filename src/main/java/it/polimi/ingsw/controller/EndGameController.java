package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.fromServer.CommunicateWinner;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class EndGameController {

    private Game game;
    private Controller controller;

    public EndGameController(Game game, Controller controller){
        this.game=game;
        this.controller=controller;
    }

    public void check(){
        //Check if a player has no tower left in his SchoolBoard
        for (Player player: game.getOnlinePlayers()) {
            //TODO
            //if (player.getSchoolBoard().getTowersNumber()==0){
              //controller.notify(new CommunicateWinner(player.getNickname(), "The player placed all the towers."));
            //}
        }

        //Check if there are only 3 groups of Islands
        if (game.getDashboard().getIslands().size()<4 || game.getFinalRound()){
            //The player who has built the most Towers on Islands wins the game
            //In case of a tie, the player who controls the most Professors wins the game
            Map<Player, Integer> towersMap = new HashMap<>();
            for (Tower tower:game.getTeams()) {
                towersMap.put(game.getTeamFromTower(tower).get(0), game.getTeamFromTower(tower).get(0).getSchoolBoard().getTowersNumber());
            }
            Integer min = Collections.min(towersMap.values());
            List<Player> minTowers;
            minTowers = towersMap.entrySet().stream()
                    .filter(Integer -> Objects.equals(Integer.getValue(), min))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            if (minTowers.size()==1){
                controller.notify(new CommunicateWinner(minTowers.get(0).getNickname(), "There are only 3 groups of Islands left. The player has won because of the number of Towers placed"));
            }
            else {
                //Tie: check for the Professor
                Map<Player, Integer> professorsMap = new HashMap<>();
                for (Player player:minTowers) {
                    professorsMap.put(player, player.getSchoolBoard().getProfessors().size());
                }
                Integer max = Collections.max(professorsMap.values());
                List<Player> maxProfessors;
                maxProfessors = professorsMap.entrySet().stream()
                        .filter(Integer -> Objects.equals(Integer.getValue(), max))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                //TODO in case of the same professors number

                controller.notify(new CommunicateWinner(maxProfessors.get(0).getNickname(), "There are only 3 groups of Islands left. The player has won because of the number of Professors."));
            }
        }
    }

    public void isFinalRound() {
        if (game.getDashboard().getBag().getStudentsLeft() == 0 || game.getCurrentPlayer().getAvailableAssistants().size() == 0) {
            game.setFinalRound();
            controller.notify(CommunicationMessage.LAST_ROUND);
        }
    }

}
