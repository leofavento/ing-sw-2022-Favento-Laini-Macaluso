package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.EndOfTheGame;
import it.polimi.ingsw.controller.states.Setup;
import it.polimi.ingsw.controller.states.State;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Player;

import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class Controller implements Observer<Message>, Observable<Message> {
    private final Game game;
    private State state;
    private CharacterController characterController;
    //VirtualView virtualView;

    private final List<Observer<Message>> observers = new ArrayList<>();

    public Controller(Game game) {
        //this.virtualView=v;
        this.game = game;
        setState(new Setup(game, this));
    }

    public void setState(State state) {
        this.state = state;
        getState().addObserver(this);
    }


    public void updateTurnOrder() {
        game.getOnlinePlayers().sort(Comparator.comparingInt((Player p) -> p.getPlayedAssistant().getValue()));
    }

    public State getState() {
        return state;
    }


    //Method that calculates the influence of all players and sets the right Tower

    public void resolveIsland(Island island) {
            HashMap<Tower, Integer> tempHash = new HashMap<>();

            //If character9 effect is active
            if ((game.getDashboard().getDoNotCountColor())!=null) {
                for (Tower tower : game.getTeams()) {
                    tempHash.put(tower, island.noColorInfluence(game.getTeamFromTower(tower), game.getDashboard().getDoNotCountColor()));
                }
            }

            //If character6 effect is active
            else if(game.getDashboard().getDoNotCountTowers()){
                for (Tower tower : game.getTeams()) {
                    tempHash.put(tower, island.noTowerInfluence(game.getTeamFromTower(tower)));
                }
            }

            //In a normal case
            else {
                for (Tower tower : game.getTeams()) {
                    tempHash.put(tower, island.countInfluence(game.getTeamFromTower(tower)));
                }
            }

                Integer max = Collections.max(tempHash.values());
                List<Tower> maxTowers;
                maxTowers = tempHash.entrySet().stream()
                        .filter(Tower -> Objects.equals(Tower.getValue(), max))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                if (maxTowers.size() == 1) {
                    if (!(island.getTowerColor() == maxTowers.get(0))) {
                        //if Island has already a tower
                        if (island.hasTower()) {
                            //put existing tower in team SchoolBoard
                            game.getTeamFromTower(island.getTowerColor()).get(0).getSchoolBoard().addTower();
                        }
                        //add new tower
                        island.setTowers(maxTowers.get(0));
                        //remove new tower from the right team SchoolBoard
                        game.getTeamFromTower(maxTowers.get(0)).get(0).getSchoolBoard().removeTower();
                        notify(new IslandOwner(island, game.getTeamFromTower(maxTowers.get(0)).get(0).getNickname()));
                        checkMerge(island);
                        check();
                    }
                } else {
                    notify(CommunicationMessage.NO_CHANGES);
                }
        }

        public void checkMerge(Island island){
            int islandIndex = game.getDashboard().getIslands().indexOf(island);
            if (game.getDashboard().getIslands().get(islandIndex+1).getTowerColor()==island.getTowerColor()){
                game.getDashboard().mergeIslands(island, game.getDashboard().getIslands().get(islandIndex+1));
                notify(CommunicationMessage.UNIFIED_ISLANDS);
                notify(new UpdateBoard(game.getDashboard().getPlayedCharacters(), game.getDashboard(), game.getOnlinePlayers()));
                check();
                checkMerge(island);
            }
            else if (game.getDashboard().getIslands().get(islandIndex-1).getTowerColor()==island.getTowerColor()){
                game.getDashboard().mergeIslands(island, game.getDashboard().getIslands().get(islandIndex-1));
                notify(CommunicationMessage.UNIFIED_ISLANDS);
                notify(new UpdateBoard(game.getDashboard().getPlayedCharacters(), game.getDashboard(), game.getOnlinePlayers()));
                check();
                checkMerge(island);
            }

        }

        public void motherNatureMovement (int steps){
            game.getDashboard().moveMotherNature(steps);
            if (!(game.getDashboard().getIslands().get(steps).getNoEntry()==0)){
                game.getDashboard().getIslands().get(game.getDashboard().getMotherNaturePosition()).useNoEntry();
                notify(CommunicationMessage.NO_ENTRY_TILE_ON_ISLAND);
            //add back the No Entry Tile on the character
            //only in Char5 this method has effect, in every other is only void
                for (CharacterCard characterCard:game.getDashboard().getCharacters())
                {
                    characterCard.addNoEntryTile();
                }
            }
            //Island is free of No Entry Tiles
            else{
            resolveIsland(game.getDashboard().getIslands().get(steps));
         }
            game.getDashboard().setAdditionalMNMovements(0);
            notify(new UpdateBoard(game.getDashboard().getPlayedCharacters(), game.getDashboard(), null));
        }



    public CharacterController getCharacterController() {
        return characterController;
    }

    public void check(){
        //Check if a player has no tower left in his SchoolBoard
        ArrayList<Player> ListOfWinners= new ArrayList<>();
        Map<Player, Integer> towersMap = new HashMap<>();
        for (Tower tower:game.getTeams()) {
            towersMap.put(game.getTeamFromTower(tower).get(0), game.getTeamFromTower(tower).get(0).getSchoolBoard().getTowersNumber());
        }
        Integer min = Collections.min(towersMap.values());

        //A player/team has placed the last tower
        if (min==0){
            List<Player> lastTower;
            lastTower = towersMap.entrySet().stream().filter(Integer -> Objects.equals(Integer.getValue(), min))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            ListOfWinners.add(game.getTeamFromPlayer(lastTower.get(0)).get(0));
            ListOfWinners.add(game.getTeamFromPlayer(lastTower.get(0)).get(1));
            game.setWinners(ListOfWinners.get(0), ListOfWinners.get(1));

            setState(new EndOfTheGame(game, this, EndOfGameReason.LAST_TOWER_BUILD));

        }

        //Check if there are only 3 groups of Islands or if this is last round
        if (game.getDashboard().getIslands().size()<4 || game.getFinalRound()){
            //The player who has built the most Towers on Islands wins the game
            //In case of a tie, the player who controls the most Professors wins the game
            List<Player> minTowers;
            minTowers = towersMap.entrySet().stream()
                    .filter(Integer -> Objects.equals(Integer.getValue(), min))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            //Check for the player with the minimum number of towers
            if (minTowers.size()==1){
                ListOfWinners.add(game.getTeamFromPlayer(minTowers.get(0)).get(0));
                ListOfWinners.add(game.getTeamFromPlayer(minTowers.get(0)).get(1));
                game.setWinners(ListOfWinners.get(0), ListOfWinners.get(1));
                if (game.getDashboard().getIslands().size()<4){
                    setState(new EndOfTheGame(game, this, EndOfGameReason.THREE_ISLANDS_REMAINING_TOWER));
                }
                else {
                    setState(new EndOfTheGame(game, this, EndOfGameReason.LAST_ROUND_TOWER));
                }
            }
            //Tie: check for the player with the max number of professors
            else {

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
                if (maxProfessors.size()==1){
                    ListOfWinners.add(game.getTeamFromPlayer(maxProfessors.get(0)).get(0));
                    ListOfWinners.add(game.getTeamFromPlayer(maxProfessors.get(0)).get(1));
                    game.setWinners(ListOfWinners.get(0), ListOfWinners.get(1));
                    if (game.getDashboard().getIslands().size()<4){
                        setState(new EndOfTheGame(game, this, EndOfGameReason.THREE_ISLANDS_REMAINING_PROFESSOR));
                    }
                    else {
                        setState(new EndOfTheGame(game, this, EndOfGameReason.LAST_ROUND_PROFESSOR));
                    }
                }

                //In case of tie the game ends with no winner
                else {
                    if (game.getDashboard().getIslands().size()<4){
                        setState(new EndOfTheGame(game, this, EndOfGameReason.THREE_ISLANDS_REMAINING_TIE));
                    }
                    else {
                        setState(new EndOfTheGame(game, this, EndOfGameReason.LAST_ROUND_TIE));
                    }

                }
            }
        }
    }





    public void isFinalRound() {
        //Check if the bag has no students left or the player has no assistant left to play
        if (game.getDashboard().getBag().getStudentsLeft() == 0 || game.getCurrentPlayer().getAvailableAssistants().size() == 0) {
            game.setFinalRound();
            notify(CommunicationMessage.LAST_ROUND);
        }
    }


    @Override
    public void update(Message message) {
        notify(message);
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
