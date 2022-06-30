package it.polimi.ingsw.client.gui.controllers.initial;

import it.polimi.ingsw.client.gui.FxmlScenes;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.messages.fromClient.SetGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class newGameController implements Controller, Initializable {

    private GUI gui;

    @FXML
    private CheckBox myCheckBox;
    private final Integer[] numPlayers = {2, 3, 4};
    @FXML private Label errorMessage;

    @FXML
    private Label modeLabel;
    @FXML
    private ChoiceBox<Integer> numberChoiceBox;


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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberChoiceBox.getItems().addAll(numPlayers);
    }

    /**
     * method used to handle the selection of the expert mode option
     * @param event the selection/deselection of the expert mode
     */
    public void change(ActionEvent event){
        if (myCheckBox.isSelected()){
            modeLabel.setText("You will play with Expert mode rules.");
        }
        else {
            modeLabel.setText("You will play with Normal mode rules.");
        }
    }

    /**
     * method used to handle the creation of a new game and its options
     */
    public void createGame(){
        if (numberChoiceBox.getValue()==null){
            errorMessage.setText("Please select the number of players.");
            errorMessage.setVisible(true);
        }
        else {
            gui.getView().setTotalPlayers(numberChoiceBox.getValue());
            gui.getView().setActivePlayers(1);
            gui.getView().setExpertMode(myCheckBox.isSelected());
            gui.getClient().sendMessage(new SetGame(numberChoiceBox.getValue(), myCheckBox.isSelected()));
        }
    }

    public void back() {
        gui.updateScene(FxmlScenes.LOBBY.getPhase());
    }
}
