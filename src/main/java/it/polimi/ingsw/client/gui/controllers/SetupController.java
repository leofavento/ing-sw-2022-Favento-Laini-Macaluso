package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.fromClient.ChosenTower;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.messages.fromServer.AvailableTowers;
import it.polimi.ingsw.messages.fromServer.AvailableWizards;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

public class SetupController implements Controller{
    @FXML private Button wizard1;
    @FXML private Button wizard2;
    @FXML private Button wizard3;
    @FXML private Button wizard4;
    @FXML private Text instruction;
    @FXML private Text errorMessage;
    @FXML private Text currentBlack;
    @FXML private Button black;
    @FXML private Text currentGrey;
    @FXML private Button grey;
    @FXML private Text currentWhite;
    @FXML private Button white;
    private GUI gui;

    public void initialize() {
        instruction.setText("Please wait...");
        black.setDisable(true);
        grey.setDisable(true);
        white.setDisable(true);
        wizard1.setDisable(true);
        wizard2.setDisable(true);
        wizard3.setDisable(true);
        wizard4.setDisable(true);
    }

    public void update(AvailableTowers availableTowers) {
        instruction.setText("Select your tower");
        HashMap<Tower, Integer> positionsLeft = availableTowers.getAvailableTowers();
        if (positionsLeft.containsKey(Tower.BLACK)) {
            black.setDisable(positionsLeft.get(Tower.BLACK) < 1);
        }
        if (positionsLeft.containsKey(Tower.GREY)) {
            grey.setDisable(positionsLeft.get(Tower.GREY) < 1);
        }
        if (positionsLeft.containsKey(Tower.WHITE)) {
            white.setDisable(positionsLeft.get(Tower.WHITE) < 1);
        }
        EnumMap<Tower, ArrayList<Player>> currentTeams = availableTowers.getCurrentTeams();
        if (currentTeams.containsKey(Tower.BLACK)) {
            currentBlack.setText(buildTeamString(currentTeams, Tower.BLACK));
        }
        if (currentTeams.containsKey(Tower.GREY)) {
            currentGrey.setText(buildTeamString(currentTeams, Tower.GREY));
        }
        if (currentTeams.containsKey(Tower.WHITE)) {
            currentWhite.setText(buildTeamString(currentTeams, Tower.WHITE));
        }
    }

    public void update(AvailableWizards availableWizards) {
        instruction.setText("Select your wizard");
        ArrayList<Integer> available = availableWizards.getAvailableWizards();
        if (available.contains(1)) {
            wizard1.setDisable(false);
        }
        if (available.contains(2)) {
            wizard2.setDisable(false);
        }
        if (available.contains(3)) {
            wizard3.setDisable(false);
        }
        if (available.contains(4)) {
            wizard4.setDisable(false);
        }
    }

    private String buildTeamString(EnumMap<Tower, ArrayList<Player>> currentTeams, Tower tower) {
        StringBuilder teamBuilder = new StringBuilder("Current players: ");
        for (Player player : currentTeams.get(tower)) {
            teamBuilder.append(player.getNickname()).append(String.format("%s",
                    currentTeams.get(tower).indexOf(player) == currentTeams.get(tower).size() - 1 ? "" : ", "));
        }
        return teamBuilder.toString();
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
    public void nextPhase() {

    }

    public void selectBlack() {
        gui.getClient().sendMessage(new ChosenTower(Tower.BLACK));
        black.setDisable(true);
        grey.setDisable(true);
        white.setDisable(true);
        instruction.setText("Please wait...");
    }

    public void selectGrey() {
        gui.getClient().sendMessage(new ChosenTower(Tower.GREY));
        black.setDisable(true);
        grey.setDisable(true);
        white.setDisable(true);
        instruction.setText("Please wait...");
    }

    public void selectWhite() {
        gui.getClient().sendMessage(new ChosenTower(Tower.WHITE));
        black.setDisable(true);
        grey.setDisable(true);
        white.setDisable(true);
        instruction.setText("Please wait...");
    }

    public void selectWizard1() {
        gui.getClient().sendMessage(new ChosenWizard(1));
        wizard1.setDisable(true);
        wizard2.setDisable(true);
        wizard3.setDisable(true);
        wizard4.setDisable(true);
        instruction.setText("Please wait...");
    }

    public void selectWizard2() {
        gui.getClient().sendMessage(new ChosenWizard(2));
        wizard1.setDisable(true);
        wizard2.setDisable(true);
        wizard3.setDisable(true);
        wizard4.setDisable(true);
        instruction.setText("Please wait...");
    }

    public void selectWizard3() {
        gui.getClient().sendMessage(new ChosenWizard(3));
        wizard1.setDisable(true);
        wizard2.setDisable(true);
        wizard3.setDisable(true);
        wizard4.setDisable(true);
        instruction.setText("Please wait...");
    }

    public void selectWizard4() {
        gui.getClient().sendMessage(new ChosenWizard(4));
        wizard1.setDisable(true);
        wizard2.setDisable(true);
        wizard3.setDisable(true);
        wizard4.setDisable(true);
        instruction.setText("Please wait...");
    }
}
