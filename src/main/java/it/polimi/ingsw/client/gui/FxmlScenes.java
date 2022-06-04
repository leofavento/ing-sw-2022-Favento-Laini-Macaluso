package it.polimi.ingsw.client.gui;

public enum FxmlScenes {
    CONNECTION("connection.fxml"),
    NICKNAME("nickname.fxml"),
    LOBBY("lobby.fxml");

    private final String phase;

    public String getPhase() {
        return phase;
    }

    FxmlScenes(String phase) {
        this.phase = phase;
    }
}
