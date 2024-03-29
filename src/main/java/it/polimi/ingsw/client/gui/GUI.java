package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.client.gui.controllers.initial.ConnectionController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GUI extends Application {
    private Client client;
    private View view;
    private Stage primaryStage;
    private Scene currentScene;
    private String currentPhase;
    private HashMap<String, Scene> scenesMap = new HashMap<>();
    private HashMap<String, Controller> controllersMap = new HashMap<>();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initializeStage(false);
        primaryStage = stage;

        Image image = new Image("graphics/eriantys.png");

        primaryStage.getIcons().add(image);


        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            if (client != null) {
                client.closeConnection();
            }
            System.exit(0);
        });
        execute();
    }

    public void close() {
        client.closeConnection();
    }

    public void resetMaps() {
        scenesMap = new HashMap<>();
        controllersMap = new HashMap<>();
    }

    public void initializeStage(boolean disconnection) {
        List<FxmlScenes> fxmlPaths = new ArrayList<>(Arrays.asList(FxmlScenes.values()));
        try {
            for (FxmlScenes path : fxmlPaths) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/" + path.getPhase()));
                scenesMap.put(path.getPhase(), new Scene(loader.load()));
                Controller controller = loader.getController();
                controller.setGui(this);
                controllersMap.put(path.getPhase(), controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPhase = FxmlScenes.CONNECTION.getPhase();
        currentScene = scenesMap.get(FxmlScenes.CONNECTION.getPhase());
        if (disconnection) {
            ((ConnectionController) controllersMap.get(currentPhase)).showDisconnected();
        }
    }

    public void execute() {
        primaryStage.setMaximized(false);
        primaryStage.setTitle("Eriantys");
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public String getPhase() {
        return currentPhase;
    }

    public void updateScene(String nextPhase) {
        Platform.runLater(() -> {
            Scene nextScene = scenesMap.get(nextPhase);
            currentPhase = nextPhase;
            primaryStage.setScene(nextScene);
            primaryStage.show();
            if (Objects.equals(nextPhase, FxmlScenes.DASHBOARD.getPhase())){
                primaryStage.setMaximized(true);
            }
        });
    }

    public void errorMessage(String error) {
        controllersMap.get(currentPhase).error(error);
    }

    public Controller getCurrentController() {
        return controllersMap.get(currentPhase);
    }

    public Controller getController(String phase) {
        return controllersMap.get(phase);
    }
}
