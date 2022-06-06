package it.polimi.ingsw.client.gui.controllers.initial;

import it.polimi.ingsw.client.gui.FxmlScenes;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.messages.fromClient.JoinAvailableGame;
import it.polimi.ingsw.messages.fromClient.RequestGames;
import it.polimi.ingsw.model.GameInfo;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class LobbyController implements Controller {
    @FXML private Text errorMessage;
    @FXML private TableColumn<GameInfo, Integer> gameID;
    @FXML private TableColumn<GameInfo, String> players;
    @FXML private TableColumn<GameInfo, String> expertMode;
    @FXML private Button refresh;
    @FXML private Button join;
    @FXML private Button create;
    @FXML private TableView<GameInfo> games;
    private GUI gui;

    @FXML
    public void initialize() {
        join.disableProperty().bind(Bindings.isEmpty(games.getSelectionModel().getSelectedItems()));
        gameID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getGameID()).asObject());
        players.setCellValueFactory(data -> new SimpleStringProperty(String
                .format("%d/%d", data.getValue().getNumOfWaitingPlayers(), data.getValue().getNumOfTotalPlayers())));
        expertMode.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()
                .isExpertGame() ? "enabled" : "disabled"));
    }

    public void updateTable(ArrayList<GameInfo> availableGames) {
        games.getItems().clear();
        for (GameInfo game : availableGames) {
            games.getItems().add(game);
        }
    }

    public void requestGames() {
        gui.getClient().sendMessage(new RequestGames());
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

    public void joinGame() {
        int selectedGame = games.getSelectionModel().getSelectedItem().getGameID();
        gui.getClient().sendMessage(new JoinAvailableGame(selectedGame));
    }

    public void createGame() {
        gui.updateScene(FxmlScenes.NEW_GAME.getPhase());
    }
}
