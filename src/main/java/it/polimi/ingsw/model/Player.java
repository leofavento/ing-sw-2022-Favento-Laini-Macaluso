package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

        private final String nickname;
        private boolean isOnline;
        private int wizardID;
        private final ArrayList<Assistant> deck;
        private Assistant playedAssistant;
        private Cloud chosenCloud;
        private final SchoolBoard schoolBoard;


        public Player(String nickname){
            this.nickname = nickname;
            deck= new ArrayList<>();
            schoolBoard = new SchoolBoard();
        }

        public String getNickname(){
            return nickname;
        }

        public void setWizardID(int wizardID){
            this.wizardID= wizardID;
        }

        public int getWizardID(){
            return wizardID;
        }

        public void createDeck(){
                List<Assistant> deck = Arrays.asList(Assistant.values());
        }

        public void playAssistant(Assistant assistant){
                try{
                        deck.remove(assistant);
                }
                catch (Exception alreadyUsedAssistant){
                        System.out.println("Assistente già utilizzato");
                }
                this.playedAssistant=assistant;

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

}
