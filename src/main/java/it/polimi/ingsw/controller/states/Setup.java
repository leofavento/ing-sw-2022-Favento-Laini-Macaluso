package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Action;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Setup implements State {
    Message requestedMessage;
    String requestedSender; // maybe not needed (should always be currentPlayer)

    private final List<Observer<Message>> observers = new ArrayList<>();

    @Override
    public State nextState() {
        return new Planning();
    }

    @Override
    public void execute(Game game) {
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
        game.initialTowersDeal();
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
    }

    @Override
    public void receiveMessage(Message message, String sender) {
        if (! message.getClass().equals(requestedMessage.getClass())) {

        } else if (! sender.equals(requestedSender)) {

        } else {

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
