package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Dashboard {
        private final ArrayList<Island> islands;
        private final Character[] characters;
        private final ArrayList<Cloud> clouds;
        private final Professor[] professors;
        private final MotherNature motherNature;
        private final Bag bag;

        public Dashboard(){
                this.islands= new ArrayList<>();
                this.clouds = new ArrayList<>();
                this.characters= new Character[3];
                this.professors= new Professor[5];
                motherNature = new MotherNature();
                bag = new Bag();
        }

        public ArrayList<Island> getIslands(){
                return islands;
        }

        public void placeIslands(){
                for(int i = 1; i <= 12; i++) {
                        islands.add(new Island(i));
                }
        }

        public void placeCloudTiles(int number){
                for(int i = 0; i < number; i++) {
                        clouds.add(new Cloud());
                }
        }

        public void MoveMotherNature(int steps){
                motherNature.setIsland((motherNature.getIsland() + steps) % islands.size());
        }

        public void deleteIsland(int islandID){
                islands.removeIf(island -> (island.getID()==islandID));
        }

        public int countTowers(Player player){
                return islands.stream()
                        .filter(i -> i.hasTower())
                        .filter(i -> i.getTowerColor() == player.getSchoolBoard().getTowerColor())
                        .mapToInt(i -> i.getNumUnits())
                        .sum();
        }

        public void mergeIslands(Island a, Island ... merging) {
                for (Island t : merging) {
                        a.addIsland(t);
                        deleteIsland(t.getID());
                }
        }

        public Professor[] getProfessors() {
                return professors;
        }
}
