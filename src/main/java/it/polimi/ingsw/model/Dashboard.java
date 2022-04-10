package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Dashboard {
        private ArrayList<Island> islands;
        private Character[] characters;
        private ArrayList<Cloud> clouds;
        private Professor[] professors;


        public Dashboard(){
                this.islands= new ArrayList<>();
                this.clouds = new ArrayList<>();
                this.characters= new Character[3];
                this.professors= new Professor[5];
        }

        public ArrayList<Island> getIslands(){
                return islands;
        }

        public void placeIslands(){
                //TODO
        }

        public void placeCloudTiles(){
                //TODO
        }

        public void MoveMotherNature(int steps){
                //TODO
        }

        public void deleteIsland(int islandID){
                islands.removeIf(island -> (island.getID()==islandID));
        }

        public void countTowers(Player player){
                //TODO
        }

        public void updateProfessors(){
                //TODO
        }
}
