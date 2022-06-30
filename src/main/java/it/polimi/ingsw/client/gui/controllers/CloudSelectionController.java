package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.FxmlScenes;
import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.awt.*;

public class CloudSelectionController implements Controller{
    private Text error;
    private GUI gui;

    @FXML public Button cloud0;
    @FXML public Button cloud1;
    @FXML public Button cloud2;
    @FXML public Button cloud3;


    /**
     * method used to update the visibility of the cloud buttons
     * @param clouds the number of clouds, depending on the game's number of players
     */
    public void updateCloudButtons(int clouds){

        Button[] buttons = new Button[]{cloud0, cloud1, cloud2, cloud3};

        for(int i=0; i<4; i++){
            buttons[i].setVisible(false);
        }

        switch (clouds){
            case 2 -> {
                buttons[0].setVisible(true);
                buttons[1].setVisible(true);
            }
            case 3 -> {
                buttons[0].setVisible(true);
                buttons[1].setVisible(true);
                buttons[2].setVisible(true);
            }

            case 4 -> {
                buttons[0].setVisible(true);
                buttons[1].setVisible(true);
                buttons[2].setVisible(true);
                buttons[3].setVisible(true);
            }
        }
    }

    /**
     * method used to handle the selection of the first cloud
     */
    public void cloud0Action(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendCloudSelection(0);
    }

    /**
     * method used to handle the selection of the second cloud
     */
    public void cloud1Action(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendCloudSelection(1);
    }

    /**
     * method used to handle the selection of the third cloud
     */
    public void cloud2Action(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendCloudSelection(2);
    }

    /**
     * method used to handle the selection of the fourth cloud
     */
    public void cloud3Action(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendCloudSelection(3);
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
}
