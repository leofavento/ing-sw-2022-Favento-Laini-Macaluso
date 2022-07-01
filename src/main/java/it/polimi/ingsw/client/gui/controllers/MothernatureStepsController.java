package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.fromClient.ChosenSteps;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class MothernatureStepsController implements Controller {
    @FXML private ChoiceBox<Integer> stepsNumber;
    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void error(String error) {//unused method
    }

    @Override
    public void nextPhase() {//unused method
    }

    /**
     * method used to set the number of possible MotherNature steps
     * @param maxSteps maximum MotherNature steps
     */
    public void loadSteps(int maxSteps) {
        ObservableList<Integer> choices = FXCollections.observableArrayList();
        for (int i = 1; i <= maxSteps; i++) {
            choices.add(i);
        }
        stepsNumber.setItems(choices);
        stepsNumber.setValue(choices.get(0));
    }

    public void move() {
        gui.getClient().sendMessage(new ChosenSteps(stepsNumber.getValue()));
    }
}
