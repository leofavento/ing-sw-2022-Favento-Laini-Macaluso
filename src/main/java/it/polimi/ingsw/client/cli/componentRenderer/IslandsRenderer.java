package it.polimi.ingsw.client.cli.componentRenderer;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Tower;


public class IslandsRenderer {

    /**
     * This method prints to command line all the islands on the dashboard and their students.
     * If an island has Mother Nature on it, this method prints "+ M" next to the respective island
     * If an island has a No Entry tile on it, this method prints "+ NO ENTRY" next to the respective island
     *
     * @param dashboard the game dashboard
     */
    public static void islandsRenderer(Dashboard dashboard){

        System.out.println("|-----------------------------");

        for(int i=0; i<dashboard.getIslands().size(); i++){
            int x = i+1;
            System.out.print("Island " + x);

            if(i == dashboard.getMotherNaturePosition()){
                System.out.print("+ M");
            }

            if(dashboard.getIslands().get(i).getNoEntry() > 0){
                System.out.print("+ NO ENTRY");
            }
            System.out.print(":\n");

            System.out.println("Green students: " + dashboard.getIslands().get(i).getStudents().stream().filter(a->a == Color.GREEN).count());
            System.out.println("Red students: " + dashboard.getIslands().get(i).getStudents().stream().filter(a->a == Color.RED).count());
            System.out.println("Yellow students: " + dashboard.getIslands().get(i).getStudents().stream().filter(a->a == Color.YELLOW).count());
            System.out.println("Pink students: " + dashboard.getIslands().get(i).getStudents().stream().filter(a->a == Color.PINK).count());
            System.out.println("Blue students: " + dashboard.getIslands().get(i).getStudents().stream().filter(a->a == Color.BLUE).count());


            if(dashboard.getIslands().get(i).hasTower()){
                Tower towerColor = dashboard.getIslands().get(i).getTowerColor();
                System.out.println( towerColor.toString() + " towers: " + dashboard.getIslands().get(i).getNumUnits());
            }

            System.out.println("|-----------------------------");
        }
    }
}
