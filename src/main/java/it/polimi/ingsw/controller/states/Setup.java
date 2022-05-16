package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Action;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Setup implements State {
    Game game;
    Controller controller;
    boolean requestedTower = false;
    boolean requestedWizardID = false;
    boolean requestedAck = false;
    Map<Tower, Integer> availableTowers = new HashMap<>();
    ArrayList<Integer> availableWizards = new ArrayList<>();
    ArrayList<String> missingAcks = new ArrayList<>();

    private final List<Observer<Message>> observers = new ArrayList<>();

    public Setup(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void nextState() {
        controller.setState(new Planning(game, controller));
        controller.getState().execute();
    }

    @Override
    public void execute() {
        game.getDashboard().placeIslands();
        game.getDashboard().setMotherNature(game.getDashboard().getIslands().get(0));
        game.getDashboard().getBag().refill(2);
        for (int i = 0; i < 12; i++) {
            if (i == game.getDashboard().getMotherNaturePosition()
                    || i == (game.getDashboard().getMotherNaturePosition() + 6) % 12) {
                game.getDashboard().getIslands().get(i).addStudent(game.getDashboard().getBag().drawStudent());
            }
        }
        game.getDashboard().getBag().refill(24);
        game.getDashboard().placeCloudTiles(game.getNumberOfPlayers());
        
        if (game.getNumberOfPlayers() == 2) {
            availableTowers.put(Tower.BLACK, 1);
            availableTowers.put(Tower.WHITE, 1);
        } else if (game.getNumberOfPlayers() == 3) {
            availableTowers.put(Tower.BLACK, 1);
            availableTowers.put(Tower.WHITE, 1);
            availableTowers.put(Tower.GREY, 1);
        } else if (game.getNumberOfPlayers() == 4) {
            availableTowers.put(Tower.BLACK, 2);
            availableTowers.put(Tower.WHITE, 2);
        }
        
        availableWizards = (ArrayList<Integer>) IntStream.range(1, 5).boxed().collect(Collectors.toList());

        requestedTower = true;
        notify(new AvailableTowers(availableTowers));
    
    }
    
    /**
     * Checks if every tower has been chosen. If there are still towers left the current player
     * is asked to enter his desired tower color, otherwise the method proceeds in the setup
     */
    private void checkTowers() {
        for (Integer i : availableTowers.values()) {
            if (i > 0) {
                requestedTower = true;
                notify(new AvailableTowers(availableTowers));
                return;
            }
        }
        game.initialTowersDeal();
        requestedWizardID = true;
        notify(new AvailableWizards(availableWizards));
    }

    private void checkWizards() {
        if (game.getOnlinePlayers().stream().anyMatch(player -> player.getWizardID() == 0)) {
            requestedWizardID = true;
            notify(new AvailableWizards(availableWizards));
            return;
        }
        for (Player player : game.getOnlinePlayers()) {
            switch (game.getNumberOfPlayers()) {
                case (2):
                case (4):
                    Action.moveFromBagToDeposit(game.getDashboard().getBag(),
                            player.getSchoolBoard().getEntrance(),
                            7);
                    break;
                case (3):
                    Action.moveFromBagToDeposit(game.getDashboard().getBag(),
                            player.getSchoolBoard().getEntrance(),
                            9);
                    break;
            }
        }
        Collections.shuffle(game.getOnlinePlayers());
        game.setCurrentPlayer(game.getOnlinePlayers().get(0));
        missingAcks.addAll(game.getOnlinePlayers().stream()
                .map(Player::getNickname)
                .collect(Collectors.toList()));
        notify(new UpdateBoard(null, game.getDashboard()));
        requestedAck = true;
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (message instanceof ChosenTower && requestedTower) {
            receiveTower((ChosenTower) message);
        } else if (message instanceof ChosenWizard && requestedWizardID) {
            receiveWizard((ChosenWizard) message);
        } else if (message instanceof Ack && requestedAck) {
            receiveAck(sender);
        }
    }

    /**
     * If the player hasn't already chosen a tower color before and the selected tower choice is
     * still available, the player gets added to the team and the setup proceeds.
     * If the player had already chosen a color or the tower color wasn't available, the server
     * sends an ErrorMessage
     * @param message message sent from client to server containing the desired tower color
     */
    private void receiveTower(ChosenTower message) {
        Tower chosenTower = message.getColor();
        if (game.getCurrentPlayer().getSchoolBoard().getTowerColor() == null) {
            if (availableTowers.get(chosenTower) > 0) {
                requestedTower = false;
                availableTowers.put(chosenTower, availableTowers.get(chosenTower) - 1);
                game.addPlayerToTeam(chosenTower, game.getCurrentPlayer());
                game.getCurrentPlayer().getSchoolBoard().setTowerColor(chosenTower);
                notify(CommunicationMessage.SUCCESS);
                game.setNextPlayer();
                checkTowers();
            } else {
                notify(ErrorMessage.TOWER_NOT_AVAILABLE);
                notify(new AvailableTowers(availableTowers));
            }
        } else {
            notify(ErrorMessage.ALREADY_RECEIVED);
        }
    }

    private void receiveWizard(ChosenWizard message) {
        Integer chosenWizard = message.getChosenWizard();
        if (game.getCurrentPlayer().getWizardID() == 0) {
            if (availableWizards.contains(chosenWizard)) {
                requestedWizardID = false;
                availableWizards.remove(chosenWizard);
                game.getCurrentPlayer().setWizardID(chosenWizard);
                notify(CommunicationMessage.SUCCESS);
                game.setNextPlayer();
                checkWizards();
            } else {
                notify(ErrorMessage.WIZARD_NOT_AVAILABLE);
                notify(new AvailableTowers(availableTowers));
            }
        } else {
            notify(ErrorMessage.ALREADY_RECEIVED);
        }
    }

    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            nextState();
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
