package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Game {
        private ArrayList<Player> players;
        private ArrayList<Player> onlinePlayers;
        private Player currentPlayer;
        private Dashboard dashboard;

        public Game(){
            this.players=new ArrayList<>();
            this.onlinePlayers=new ArrayList<>();
        }


        //public Player getPlayer(String nickname){
            //TODO
            //don't know what this should do
        //}

        public ArrayList<Player> getOnlinePlayers(){
            return onlinePlayers;
        }

        public Player getCurrentPlayer(){
            return currentPlayer;
        }

        public void initialTowersDeal(){
            //TODO
        }

        public void setNextPlayer(){
            //TODO
        }

        public void dealStudents(Player p, int numStudents){
            //TODO
        }







}
