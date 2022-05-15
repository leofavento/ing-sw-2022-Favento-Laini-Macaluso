package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.FullDiningRoomException;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.StudentNotExistingException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observer;

public class ActionStep1 implements State {
    Game game;
    Controller controller;

    public ActionStep1(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void nextState() {
        // TODO
    }

    @Override
    public void execute() {
        int numStudent = 3;
        if (game.getNumberOfPlayers() == 3) {
            numStudent = 4;
        }

        for (int i = 0; i < numStudent; i++) {
            // TODO ask currentPlayer to select a student from his
            //  entrance and its destination (dining room or island)
            Color chosenStudent = Color.PINK;
            int destination = 4;

            try {
                checkIfValidDestination(destination, game.getDashboard().getIslands().size());
                if (destination == 0) {
                    checkIfFullDiningRoom(game.getCurrentPlayer(), chosenStudent);
                    game.getCurrentPlayer().getSchoolBoard().getEntrance().extractStudent(chosenStudent);
                    game.getCurrentPlayer().getSchoolBoard().getDiningRoom().addStudent(chosenStudent);
                } else {
                    game.getCurrentPlayer().getSchoolBoard().getEntrance().extractStudent(chosenStudent);
                    game.getDashboard().getIslands().get(destination - 1).addStudent(chosenStudent);
                }
            } catch (InvalidInputException | StudentNotExistingException | FullDiningRoomException e) {
                // TODO ask to enter student and entrance again
            }
        }
    }

    @Override
    public void receiveMessage(Message message, String sender) {

    }

    private void checkIfFullDiningRoom(Player player, Color student) throws FullDiningRoomException {
        if (player.getSchoolBoard().getDiningRoom().getStudentsNumber(student) == 10) {
            throw new FullDiningRoomException("The dining room is full for " + student.name() + " students.");
        }
    }

    private void checkIfValidDestination(int destination, int islandsSize) throws InvalidInputException {
        if (destination < 0 || destination > islandsSize) {
            throw new InvalidInputException("The destination value must be between 0 and " + islandsSize);
        }
    }

    @Override
    public void addObserver(Observer<Message> observer) {

    }

    @Override
    public void notify(Message message) {

    }
}
