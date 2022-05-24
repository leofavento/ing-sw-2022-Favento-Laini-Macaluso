package it.polimi.ingsw.client.cli.componentRenderer;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Dashboard;

public class CloudsRenderer {

    /**
     * This method prints to command line all the clouds and the students on them
     * @param dashboard the game dashboard
     */
    public static void cloudRenderer(Dashboard dashboard){
        System.out.println("|-----------------------------");
        for(int i=0; i<dashboard.getClouds().size(); i++){

            System.out.println("Cloud " + i+1 + ":");
            System.out.println("Green students: " + dashboard.getClouds().get(i).getStudents().stream().filter(a->a == Color.GREEN).count());
            System.out.println("Red students: " + dashboard.getClouds().get(i).getStudents().stream().filter(a->a == Color.RED).count());
            System.out.println("Yellow students: " + dashboard.getClouds().get(i).getStudents().stream().filter(a->a == Color.YELLOW).count());
            System.out.println("Pink students: " + dashboard.getClouds().get(i).getStudents().stream().filter(a->a == Color.PINK).count());
            System.out.println("Blue students: " + dashboard.getClouds().get(i).getStudents().stream().filter(a->a == Color.BLUE).count());

            System.out.println("|-----------------------------");
        }
    }
}
