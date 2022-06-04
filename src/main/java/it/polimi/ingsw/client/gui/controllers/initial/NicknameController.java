package it.polimi.ingsw.client.gui.controllers.initial;

import it.polimi.ingsw.client.gui.FxmlScenes;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class NicknameController implements Controller {
    private GUI gui;
    @FXML public TextField nickname;
    @FXML private Text errorMessage;

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
    public void nextPhase() {
        gui.updateScene(FxmlScenes.LOBBY.getPhase());
    }

    public void enterNickname() {
        String nick = nickname.getText();
        if (nick.equals("")) {
            errorMessage.setText("Please enter a nickname.");
            errorMessage.setVisible(true);
        } else {
            gui.getClient().setNickname(nick);
            gui.getClient().sendMessage(new LoginMessage(nick));
        }
    }
}
