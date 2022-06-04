package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;

public interface Controller {
    void setGui(GUI gui);
    void error(String error);
    void nextPhase();
}
