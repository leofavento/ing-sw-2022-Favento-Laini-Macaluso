package it.polimi.ingsw.client.gui.controllers.initial;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.model.GameInfo;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WaitingPlayersController implements Controller {
    @FXML private Text errorMessage;
    @FXML private Text message;
    private GUI gui;

    public void setMessage(String message) {
        this.message.setText(message);
        this.message.setVisible(true);
    }

    public void setGameInfo(GameInfo gameInfo) {
        gui.getView().setExpertMode(gameInfo.isExpertGame());
        gui.getView().setTotalPlayers(gameInfo.getNumOfTotalPlayers());
        gui.getView().setActivePlayers(gameInfo.getNumOfWaitingPlayers());
        update();
    }

    /**
     * method used to update the number of current players connected to a game
     */
    public void update() {
        message.setText(String.format("%d/%d players currently connected", gui.getView().getActivePlayers(),
                gui.getView().getTotalPlayers()));
        message.setVisible(true);
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void error(String error) {
        errorMessage.setText(error);
        errorMessage.setVisible(true);
    }

    @Override
    public void nextPhase() {//unused method
    }
}
