package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.Setup;
import it.polimi.ingsw.controller.states.State;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.MotherNatureSteps;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
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
    private EndGameController endGameController;
    //VirtualView virtualView;

    private final List<Observer<Message>> observers = new ArrayList<>();

    public Controller(Game game) {
        //this.virtualView=v;
        this.game = game;
        setState(new Setup(game, this));
        //characterController=new CharacterController(this, game, v);
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
            if ((!game.getDashboard().getDoNotCountTowers())) {
                for (Tower tower : game.getTeams()) {
                    tempHash.put(tower, island.countInfluence(game.getTeamFromTower(tower)));
                }
            }
            else{
                for (Tower tower : game.getTeams()) {
                    tempHash.put(tower, island.noTowerInfluence(game.getTeamFromTower(tower)));
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
                        //add tower1
                        island.setTowers(maxTowers.get(0));
                        //remove tower1 from team1 SchoolBoard
                        game.getTeamFromTower(maxTowers.get(0)).get(0).getSchoolBoard().removeTower();
                        checkMerge(island);
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
                endGameController.check();
                checkMerge(island);
            }
            else if (game.getDashboard().getIslands().get(islandIndex-1).getTowerColor()==island.getTowerColor()){
                game.getDashboard().mergeIslands(island, game.getDashboard().getIslands().get(islandIndex-1));
                notify(CommunicationMessage.UNIFIED_ISLANDS);
                notify(new UpdateBoard(game.getDashboard().getPlayedCharacters(), game.getDashboard(), game.getOnlinePlayers()));
                endGameController.check();
                checkMerge(island);
            }

        }

        public void motherNatureMovement (int steps){
            if (!(game.getCurrentPlayer().getStatus()== PlayerStatus.MOVE_2)){
                notify(ErrorMessage.INVALID_ACTION);
            }
            //provides the user the number of max steps allowed
            game.getDashboard().moveMotherNature(steps);
            if (!(game.getDashboard().getIslands().get(steps).getNoEntry()==0)){
                game.getDashboard().getIslands().get(steps).useNoEntry();
                notify(CommunicationMessage.NO_ENTRY_TILE_ON_ISLAND);
            //add back the No Entry Tile on the character
            //only in char5 this method has effect, in every other is only void
                for (CharacterCard characterCard:game.getDashboard().getCharacters())
                {
                    characterCard.addNoEntryTile();
                }
            }
            else{
            resolveIsland(game.getDashboard().getIslands().get(steps));
         }
            game.getDashboard().setAdditionalMNMovements(0);
            notify(new UpdateBoard(game.getDashboard().getPlayedCharacters(), game.getDashboard(), null));
        }

    public CharacterController getCharacterController() {
        return characterController;
    }

    public EndGameController getEndGameController(){
        return endGameController;
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
