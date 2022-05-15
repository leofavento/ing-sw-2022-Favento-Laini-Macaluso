package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Cloud;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private final String nickname;
    private boolean isOnline;
    private int wizardID;
    private final ArrayList<Assistant> availableAssistants;
    private Assistant playedAssistant;
    private Cloud chosenCloud;
    private final SchoolBoard schoolBoard;
    private PlayerStatus status;

    public Player(String nickname){
        this.nickname = nickname;
        availableAssistants = new ArrayList<>();
        wizardID = 0;
        setAssistants();
        schoolBoard = new SchoolBoard();
    }

    private void setAssistants() {
        Collections.addAll(availableAssistants, Assistant.values());
    }

    public String getNickname(){
        return nickname;
    }

    public void setWizardID(int wizardID){
        this.wizardID = wizardID;
    }

    public int getWizardID(){
        return wizardID;
    }

    public ArrayList<Assistant> getAvailableAssistants() {
        return availableAssistants;
    }

    public void playAssistant(Assistant assistant) {
        availableAssistants.remove(assistant);
        playedAssistant = assistant;
    }

    public Assistant getPlayedAssistant(){
        return playedAssistant;
    }

    public void chooseCloud(Cloud cloud){
        this.chosenCloud=cloud;
    }

    public Cloud getChosenCloud(){
        return chosenCloud;
    }

    public SchoolBoard getSchoolBoard() { return schoolBoard; }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public PlayerStatus getStatus() {
        return status;
    }
}
