package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.util.Collections;

public class Setup implements State {

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
        game.getDashboard().placeCloudTiles(game.getOnlinePlayers().size());
        switch(game.getNumberOfPlayers()) {
            case (2):
                game.getDashboard().placeCloudTiles(2);
                break;
            case (3):
                game.getDashboard().placeCloudTiles(3);
                break;
            case (4):
                game.getDashboard().placeCloudTiles(4);
                break;
        }
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
}
