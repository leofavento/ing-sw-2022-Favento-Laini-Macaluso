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
    }


    public void updateTurnOrder() {
        game.getOnlinePlayers().sort(Comparator.comparingInt((Player p) -> p.getPlayedAssistant().getValue()));
    }

    public State getState() {
        return state;
    }

    public CharacterController getCharacterController() {
        return characterController;
    }

    public void check() {
        //Check if a player has no tower left in his SchoolBoard
        ArrayList<Player> ListOfWinners = new ArrayList<>();
        Map<Player, Integer> towersMap = new HashMap<>();
        for (Tower tower : game.getTeams()) {
            towersMap.put(game.getTeamFromTower(tower).get(0), game.getTeamFromTower(tower).get(0).getSchoolBoard().getTowersNumber());
        }
        Integer min = Collections.min(towersMap.values());

        //A player/team has placed the last tower
        if (min == 0) {
            List<Player> lastTower;
            lastTower = towersMap.entrySet().stream().filter(Integer -> Objects.equals(Integer.getValue(), min))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            ListOfWinners.addAll(game.getTeamFromPlayer(lastTower.get(0)));
            game.setWinners(ListOfWinners);

            setState(new EndOfTheGame(game, this, EndOfGameReason.LAST_TOWER_BUILD));
            getState().execute();
            return;
        }

        //Check if there are only 3 groups of Islands or if this is last round
        if (game.getDashboard().getIslands().size() < 4 || game.getFinalRound()) {
            //The player who has built the most Towers on Islands wins the game
            //In case of a tie, the player who controls the most Professors wins the game
            List<Player> minTowers;
            minTowers = towersMap.entrySet().stream()
                    .filter(Integer -> Objects.equals(Integer.getValue(), min))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            //Check for the player with the minimum number of towers
            if (minTowers.size() == 1) {
                ListOfWinners.addAll(game.getTeamFromPlayer(minTowers.get(0)));
                game.setWinners(ListOfWinners);
                if (game.getDashboard().getIslands().size() < 4) {
                    setState(new EndOfTheGame(game, this, EndOfGameReason.THREE_ISLANDS_REMAINING_TOWER));
                    getState().execute();
                } else {
                    setState(new EndOfTheGame(game, this, EndOfGameReason.LAST_ROUND_TOWER));
                    getState().execute();
                }
            }
            //Tie: check for the player with the max number of professors
            else {

                Map<Player, Integer> professorsMap = new HashMap<>();
                for (Player player : minTowers) {
                    professorsMap.put(player, player.getSchoolBoard().getProfessors().size());
                }
                Integer max = Collections.max(professorsMap.values());
                List<Player> maxProfessors;
                maxProfessors = professorsMap.entrySet().stream()
                        .filter(Integer -> Objects.equals(Integer.getValue(), max))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
                if (maxProfessors.size() == 1) {
                    ListOfWinners.addAll(game.getTeamFromPlayer(maxProfessors.get(0)));
                    game.setWinners(ListOfWinners);
                    if (game.getDashboard().getIslands().size() < 4) {
                        setState(new EndOfTheGame(game, this, EndOfGameReason.THREE_ISLANDS_REMAINING_PROFESSOR));
                        getState().execute();
                    } else {
                        setState(new EndOfTheGame(game, this, EndOfGameReason.LAST_ROUND_PROFESSOR));
                        getState().execute();
                    }
                }

                //In case of tie the game ends with no winner
                else {
                    if (game.getDashboard().getIslands().size() < 4) {
                        setState(new EndOfTheGame(game, this, EndOfGameReason.THREE_ISLANDS_REMAINING_TIE));
                        getState().execute();
                    } else {
                        setState(new EndOfTheGame(game, this, EndOfGameReason.LAST_ROUND_TIE));
                        getState().execute();
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
        for (Observer<Message> o : observers) {
            o.update(message);
        }
    }

}
