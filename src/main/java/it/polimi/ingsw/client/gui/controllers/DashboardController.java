package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.player.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardController implements Controller{

    public ImageView isl7;
    @FXML private TabPane dashboard;
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
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void error(String error) {

    }

    @Override
    public void nextPhase() {

    }

    private void updateIsland1() {

    }
}
