package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.fromClient.Ack;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.player.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DashboardController implements Controller{

    @FXML private AnchorPane request;
    @FXML private TabPane dashboard;
    @FXML private Text instruction;
    @FXML private Text error;
    private HashMap<String, Tab> nicknameToTab;
    private HashMap<String, Controller> nicknameToController;
    private GUI gui;

    public void setup(ArrayList<Player> players) {
        nicknameToTab = new HashMap<>();
        nicknameToController = new HashMap<>();
        for (Player player : players) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/schoolboard.fxml"));
                nicknameToTab.put(player.getNickname(), new Tab(player.getNickname() + "'s Schoolboard"));
                AnchorPane anchorPane = loader.load();
                ScrollPane scroll = new ScrollPane();
                scroll.setContent(anchorPane);
                nicknameToTab.get(player.getNickname()).setContent(scroll);
                Controller controller = loader.getController();
                controller.setGui(gui);
                nicknameToController.put(player.getNickname(), controller);
                ((SchoolboardController) controller).resetAssistantsButtons();
                ((SchoolboardController) controller).removePlayedAssistants();
                if (!Objects.equals(player.getNickname(), gui.getClient().getNickname())) {
                    ((SchoolboardController) controller).removeAssistantButtons();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dashboard.getTabs().addAll(nicknameToTab.values());
    }

    public void update() {
        for (Player player : gui.getView().getPlayers()) {
            ((SchoolboardController) nicknameToController.get(player.getNickname())).update(player);
        }

        gui.getClient().sendMessage(new Ack());
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void error(String error) {
        this.error.setText(error);
        this.error.setVisible(true);
    }

    public void resetError() {
        error.setVisible(false);
    }

    @Override
    public void nextPhase() {

    }

    public SchoolboardController getSchoolboardController(String nickname) {
        return (SchoolboardController) nicknameToController.get(nickname);
    }
    
    public void setInstruction(String message) {
        instruction.setText(message);
    }

    public void requestAssistants() {
        dashboard.getSelectionModel().select(nicknameToTab.get(gui.getClient().getNickname()));
        ((SchoolboardController) nicknameToController.get(gui.getClient().getNickname())).requestAssistant();
        setInstruction("Select the assistant you want to play");
    }

    public void resetAssistants() {
        ((SchoolboardController) nicknameToController.get(gui.getClient().getNickname())).resetAssistantsButtons();
    }
}
