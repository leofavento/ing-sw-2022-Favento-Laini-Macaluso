package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.FxmlScenes;
import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DestinationController implements Controller{

    @FXML
    public Button buttonDining;
    @FXML
    public Button buttonIsl1;
    @FXML
    public Button buttonIsl2;
    @FXML
    public Button buttonIsl3;
    @FXML
    public Button buttonIsl4;
    @FXML
    public Button buttonIsl5;
    @FXML
    public Button buttonIsl6;
    @FXML
    public Button buttonIsl7;
    @FXML
    public Button buttonIsl8;
    @FXML
    public Button buttonIsl9;
    @FXML
    public Button buttonIsl10;
    @FXML
    public Button buttonIsl11;
    @FXML
    public Button buttonIsl12;

    private GUI gui;
    private int destination;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    /**
     * method used to handle the selection of the dining room as destination
     */
    public void actionButtonDining(){
        destination=0;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 1 as destination
     */
    public void actionButton1(){
        destination=1;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 2 as destination
     */
    public void actionButton2(){
        destination=2;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 3 as destination
     */
    public void actionButton3(){
        destination=3;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 4 as destination
     */
    public void actionButton4(){
        destination=4;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 5 as destination
     */
    public void actionButton5(){
        destination=5;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 6 as destination
     */
    public void actionButton6(){
        destination=6;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 7 as destination
     */
    public void actionButton7(){
        destination=7;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 8 as destination
     */
    public void actionButton8(){
        destination=8;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 9 as destination
     */
    public void actionButton9(){
        destination=9;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 10 as destination
     */

    public void actionButton10(){
        destination=10;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 11 as destination
     */
    public void actionButton11(){
        destination=11;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to handle the selection of island 12 as destination
     */
    public void actionButton12(){
        destination=12;
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendDestination(destination);
    }

    /**
     * method used to initialize all the destination buttons
     */
    public void initializeDestination(){
        if (gui.getView().getDashboard().getIslands().size()<12){
            buttonIsl12.setVisible(false);
            buttonIsl12.setDisable(true);
        }
        if (gui.getView().getDashboard().getIslands().size()<11){
            buttonIsl11.setVisible(false);
            buttonIsl11.setDisable(true);
        }
        if (gui.getView().getDashboard().getIslands().size()<10){
            buttonIsl10.setVisible(false);
            buttonIsl10.setDisable(true);

        }
        if (gui.getView().getDashboard().getIslands().size()<9){
            buttonIsl9.setVisible(false);
            buttonIsl9.setDisable(true);

        }
        if (gui.getView().getDashboard().getIslands().size()<8){
            buttonIsl8.setVisible(false);
            buttonIsl8.setDisable(true);

        }
        if (gui.getView().getDashboard().getIslands().size()<7){
            buttonIsl7.setVisible(false);
            buttonIsl7.setDisable(true);

        }
        if (gui.getView().getDashboard().getIslands().size()<6){
            buttonIsl6.setVisible(false);
            buttonIsl6.setDisable(true);

        }
        if (gui.getView().getDashboard().getIslands().size()<5){
            buttonIsl5.setVisible(false);
            buttonIsl5.setDisable(true);

        }
        if (gui.getView().getDashboard().getIslands().size()<4){
            buttonIsl4.setVisible(false);
            buttonIsl4.setDisable(true);

        }
        if (((gui.getView().getDashboard().getPlayedCharacter()!=null) && gui.getView().getActivatedCharacterEffect())&&(gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal()==0 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal()==2 || gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal()==4)){
            buttonDining.setVisible(false);
            buttonDining.setDisable(true);}
        }


    @Override
    public void error(String error) {//unused method
    }

    @Override
    public void nextPhase() {//unused method
    }
}
