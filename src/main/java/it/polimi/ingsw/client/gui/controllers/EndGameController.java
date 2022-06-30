package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.FxmlScenes;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.fromClient.RequestGames;
import it.polimi.ingsw.messages.fromServer.CommunicateWinner;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class EndGameController implements Controller {
    @FXML private Text congratulations;
    @FXML private Text nicknames;
    @FXML private Text reason;
    private GUI gui;

    /**
     * method used to show the end game informations (the player/team who won and the reason for the win)
     * @param message the CommunicateWinner message received
     */
   public void update(CommunicateWinner message) {
       if (message.getTeam() == null) {
           congratulations.setText("It's a tie!");
           nicknames.setText("Congratulations to everyone that played!");
       } else {
           congratulations.setText("Congratulations to the " + message.getTeam() + " team!");
           nicknames.setText(buildTeamString(message.getNicknames()));
       }
       switch(message.getWinReason()) {
           case THREE_ISLANDS_REMAINING_TIE -> reason.setText("There were only three islands remaining.");
           case LAST_ROUND_TIE -> reason.setText("It was the last round.");
           case LAST_TOWER_BUILD -> reason.setText("They built their last tower.");
           case THREE_ISLANDS_REMAINING_TOWER -> reason.setText("There are only three islands left and they " +
                   "built more towers than the others.");
           case THREE_ISLANDS_REMAINING_PROFESSOR -> reason.setText("There are only three islands left and they " +
                   "have more professors than the others.");
           case LAST_ROUND_TOWER -> reason.setText("It was the last round and they " +
                   "built more towers than the others.");
           case LAST_ROUND_PROFESSOR -> reason.setText("It was  the last round and they " +
                   "have more professors than the others.");
       }
   }

    /**
     * method used to create a string containing all the players nicknames of a team
     * @param nicknames the nicknames of all players in a team
     * @return the string containing all the nicknames of a team
     */
    private String buildTeamString(ArrayList<String> nicknames) {
        StringBuilder teamBuilder = new StringBuilder("Well played, ");
        for (String player : nicknames) {
            teamBuilder.append(player).append(String.format("%s",
                    nicknames.indexOf(player) == nicknames.size() - 1 ? "!" : ", "));
        }
        return teamBuilder.toString();
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

    public void menu() {
        gui.getClient().sendMessage(new RequestGames());
        gui.updateScene(FxmlScenes.LOBBY.getPhase());
    }
}
