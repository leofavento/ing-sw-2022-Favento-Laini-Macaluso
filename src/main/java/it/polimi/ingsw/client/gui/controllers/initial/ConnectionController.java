package it.polimi.ingsw.client.gui.controllers.initial;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIMessageReceiver;
import it.polimi.ingsw.client.gui.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class ConnectionController implements Controller {

    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private Text errorMessage;
    private GUI gui;
    int serverPort;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void connect() {
        if (ip.getText().equals("") || port.getText().equals("")) {
            errorMessage.setText("Please enter the ip and the port number.");
            errorMessage.setVisible(true);
        } else {
            try {
                serverPort = Integer.parseInt(port.getText());
            } catch (NumberFormatException e) {
                errorMessage.setText("The server port must be a number between 1024 and 65535.");
                errorMessage.setVisible(true);
                return;
            }

            if (serverPort < 1024 || serverPort > 65535) {
                errorMessage.setText("The server port must be a number between 1024 and 65535.");
                errorMessage.setVisible(true);
                return;
            }

            gui.setClient(new Client(false));
            try {
                gui.getClient().startConnection(serverPort, ip.getText());
                gui.getClient().setMessageReceiver(new GUIMessageReceiver(gui));
            } catch (IOException e) {
                errorMessage.setText("The client can't connect to the specified server.");
                errorMessage.setVisible(true);
                return;
            }
            new Thread(gui.getClient()).start();
        }
    }
}
