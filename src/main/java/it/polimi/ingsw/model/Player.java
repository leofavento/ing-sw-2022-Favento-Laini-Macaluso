package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

        private String nickname;
        private boolean isOnline;
        private int wizardID;
        private ArrayList<Assistant> deck;
        private Assistant playedAssistant;
        private Cloud chosenCloud;
        private SchoolBoard schoolBoard;


        public Player(){
            this.deck= new ArrayList<>();
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

        public void setAssistants(){
                //TODO
                //don't know what this should do
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

}
