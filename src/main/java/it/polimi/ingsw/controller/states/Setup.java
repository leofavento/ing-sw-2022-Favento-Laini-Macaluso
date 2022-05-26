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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Setup is the first phase of the game. This initializes the main components of the game.
 */
public class Setup implements State {
    Game game;
    Controller controller;
    boolean requestedTower = false;
    boolean requestedWizardID = false;
    boolean requestedAck = false;
    HashMap<Tower, Integer> availableTowers = new HashMap<>();
    ArrayList<Integer> availableWizards = new ArrayList<>();
    ArrayList<String> missingAcks = new ArrayList<>();

    public Setup(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    /**
     * method used when the setup is ended, to set the phase to Planning phase
     */
    @Override
    public void nextState() {
        controller.setState(new Planning(game, controller));
        controller.getState().execute();
    }

    /**
     * method used to create islands, to set Mother Nature initial position, to refill the bag,
     * to place cloud tiles, to initialize towers, to generate characters and to enable coins (last two only if Expert Mode is selected)
     */
    @Override
    public void execute() {
        game.getDashboard().placeIslands();
        game.getDashboard().setMotherNature(game.getDashboard().getIslands().get(0));
        game.getDashboard().getBag().refill(2);
        for (int i = 0; i < 12; i++) {
            if (i != game.getDashboard().getMotherNaturePosition()
                    && i != (game.getDashboard().getMotherNaturePosition() + 6) % 12) {
                Action.moveFromBagToDeposit(game.getDashboard().getBag(),
                        game.getDashboard().getIslands().get(i),
                        1);
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

        if (game.getExpertGame()){
            controller.getCharacterController().generateCharacters(game.getDashboard());
            for (Player p:game.getOnlinePlayers()) {
                p.getSchoolBoard().getDiningRoom().setEnableCoins();
            }
        }
        controller.notify(new AvailableTowers(availableTowers));
    
    }
    
    /**
     * method used to check if every tower has been chosen. If there are still towers left the current player
     * is asked to enter his desired tower color, otherwise the method proceeds in the setup
     */
    private void checkTowers() {
        for (Integer i : availableTowers.values()) {
            if (i > 0) {
                requestedTower = true;
                controller.notify(new AvailableTowers(availableTowers));
                return;
            }
        }
        game.initialTowersDeal();
        requestedWizardID = true;
        controller.notify(new AvailableWizards(availableWizards));
    }

    /**
     * method used to check if a player has already selected the wizard, otherwise it makes him select one.
     * Then this method adds the right number of Students to every player entrance.
     * In the end it randomly chooses the first player.
     */
    private void checkWizards() {
        if (game.getOnlinePlayers().stream().anyMatch(player -> player.getWizardID() == 0)) {
            requestedWizardID = true;
            controller.notify(new AvailableWizards(availableWizards));
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
        requestedAck = true;
        controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
    }

    /**
     * method used to receive a message from the player.
     * If the server asked for the tower and the message contains a tower, it calls the receiveTower method
     * If the server asked for a wizard and the message contains a wizard, it calls the receiveWizard method
     * If the server requires and Acknowledgment message, it calls the receiveAck method
     * @param message the instance of message
     * @param sender the nickname of the sender
     */
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
     * method used after the server received the tower preference from the player.
     * If the player hasn't already chosen a tower color before and the selected tower choice is
     * still available, the player gets added to the team and the setup proceeds.
     * If the player had already chosen a color or the tower color wasn't available, the server
     * sends an ErrorMessage
     * @param message message sent from client to server containing the desired tower color
     */
    private void receiveTower(ChosenTower message) {
        Tower chosenTower = message.getColor();
        if (game.getCurrentPlayer().getSchoolBoard().getTowerColor() == null) {
            try {
                if (availableTowers.get(chosenTower) > 0) {
                    requestedTower = false;
                    availableTowers.put(chosenTower, availableTowers.get(chosenTower) - 1);
                    game.addPlayerToTeam(chosenTower, game.getCurrentPlayer());
                    game.getCurrentPlayer().getSchoolBoard().setTowerColor(chosenTower);
                    controller.notify(CommunicationMessage.SUCCESS);
                    game.setNextPlayer();
                    checkTowers();
                } else {
                    controller.notify(ErrorMessage.TOWER_NOT_AVAILABLE);
                    controller.notify(new AvailableTowers(availableTowers));
                }
            } catch (NullPointerException e) {
                controller.notify(ErrorMessage.TOWER_NOT_AVAILABLE);
                controller.notify(new AvailableTowers(availableTowers));
            }
        } else {
            controller.notify(ErrorMessage.ALREADY_RECEIVED);
        }
    }

    /**
     * method used after the server received the wizard preference from the player.
     * @param message the message containing the selected wizard
     * If the player has not selected a wizard before, the method sets the correct wizard (if it's available).
     * Otherwise, the server notifies that the player had already selected the wizard.
     *
     */
    private void receiveWizard(ChosenWizard message) {
        Integer chosenWizard = message.getChosenWizard();
        if (game.getCurrentPlayer().getWizardID() == 0) {
            if (availableWizards.contains(chosenWizard)) {
                requestedWizardID = false;
                availableWizards.remove(chosenWizard);
                game.getCurrentPlayer().setWizardID(chosenWizard);
                controller.notify(CommunicationMessage.SUCCESS);
                game.setNextPlayer();
                checkWizards();
            } else {
                controller.notify(ErrorMessage.WIZARD_NOT_AVAILABLE);
                controller.notify(new AvailableWizards(availableWizards));
            }
        } else {
            controller.notify(ErrorMessage.ALREADY_RECEIVED);
        }
    }

    /**
     * method used to remove the ack request for a player
     * @param sender the nickname of the player who sent the Ack
     */
    private void receiveAck(String sender) {
        missingAcks.remove(sender);
        if (missingAcks.isEmpty()) {
            nextState();
        }
    }
}
