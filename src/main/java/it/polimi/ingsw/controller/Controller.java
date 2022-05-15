package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.EnumState;
import it.polimi.ingsw.controller.states.State;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.characters.Char5;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerStatus;
import it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class Controller implements Observer<Message>, Observable<Message> {
    private final Game game;
    private State state;
    private boolean isLastRound;
    //VirtualView virtualView;

    private final List<Observer<Message>> observers = new ArrayList<>();

    public Controller(Game game) {
        //this.virtualView=v;
        this.game = game;
        setState(EnumState.SETUP.getState());
    }

    public void setState(State state) {
        this.state = state;
        getState().addObserver(this);
    }

    public boolean checkIfLastRound() {
        int necessaryStudents = 0;

        switch (game.getNumberOfPlayers()) {
            case (2):
                necessaryStudents = 6;
                break;
            case (3):
            case (4):
                necessaryStudents = 12;
                break;
            default:
                // throw exception?
        }

        isLastRound = necessaryStudents < game.getDashboard().getBag().getStudentsLeft();
        return isLastRound;
    }

    public boolean getIsLastRound() {
        return isLastRound;
    }

    public void updateTurnOrder() {
        game.getOnlinePlayers().sort((Player p1, Player p2) -> Integer.compare(p1.getPlayedAssistant().getValue(), p1.getPlayedAssistant().getValue()));
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
                List<Tower> maxTowers = new ArrayList<>();
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
                    }
                } else {
                    //virtualview.sendCommunicationMessage("Draw. No changes");
                }
        }


        public void motherNatureMovement (){
            //if (!(game.getCurrentPlayer().getStatus()== PlayerStatus.MOVE_2)){
                //virtualview.sendMessage(ErrorMessage.INVALID_ACTION);
            //}
            //provides the user the number of max steps allowed
            //int num_steps=virtualview.askMNatureMovement(game.getCurrentPlayer().getPlayedAssistant().getMovements()+game.getDashboard().getAdditionalMNMovements());
            //game.getDashboard().moveMotherNature(num_steps);
            //if (!(game.getDashboard().getIslands().get(num_steps).getNoEntry()==0)){
                //game.getDashboard().getIslands().get(num_steps).useNoEntry();
                //virtualview.sendMessage(CommunicationMessage.NO_ENTRY_TILE_ON_ISLAND);
            //add back the No Entry Tile on the character
            //only in char5 this method has effect, in every other is only void
                for (CharacterCard characterCard:game.getDashboard().getCharacters())
                {
                    characterCard.addNoEntryTile();
                }
            //}
            //else{
            //notify the change to the view?
            //resolveIsland(game.getDashboard().getIslands().get(num_steps));
            //notify the change to the view?
        // }
            game.getDashboard().setAdditionalMNMovements(0);
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
