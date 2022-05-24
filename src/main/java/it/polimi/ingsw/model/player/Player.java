package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.AlreadyPlayedAssistant;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Cloud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Player implements Serializable {
    private final String nickname;
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

    public void playAssistant(Assistant assistant) throws AlreadyPlayedAssistant {
        if (! availableAssistants.contains(assistant)) {
            throw new AlreadyPlayedAssistant("This assistant was already played.");
        } else {
            availableAssistants.remove(assistant);
            playedAssistant = assistant;
        }
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
