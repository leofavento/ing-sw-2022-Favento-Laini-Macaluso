package it.polimi.ingsw.client.gui.controllers;

public enum Phases {
    MOTHERNATURE_STEPS("fxml/mothernatureSteps.fxml");

    private final String fxmlURI;

    Phases(String fxmlURI) {
        this.fxmlURI = fxmlURI;
    }

    public String getURI() {
        return fxmlURI;
    }
}
