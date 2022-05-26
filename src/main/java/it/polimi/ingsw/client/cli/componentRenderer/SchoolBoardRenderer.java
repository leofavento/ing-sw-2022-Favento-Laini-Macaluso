package it.polimi.ingsw.client.cli.componentRenderer;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.*;

import java.util.ArrayList;

public class SchoolBoardRenderer {
    public static void renderAllSchoolBoards(ArrayList<Player> players) {
        for (Player player : players) {
            renderSchoolBoard(player);
        }
    }

    /**
     * This method prints to command line the SchoolBoard of a requested Player, containing all the details
     * about the Entrance, the Dining Room and the Professors
     * @param player SchoolBoard owner
     */
    public static  void renderSchoolBoard(Player player){
        System.out.print("|--------------------------\n" +
                player.getNickname() + "'s SchoolBoard:\n" +
                "|Entrance: " +
                "green: " + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.GREEN).count() +
                ", red: " + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.RED).count() +
                ", yellow: " + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.YELLOW).count() +
                ", pink: " + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.PINK).count() +
                ", blue: " + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.BLUE).count() + "\n" +
                "|Dining Room: " +
                "green: " + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.GREEN) +
                ", red: " + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.RED) +
                ", yellow: " + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.YELLOW) +
                ", pink: " + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.PINK) +
                ", blue: " + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.BLUE) + "\n" +
                "|Professors: ");

        for(int i=0; i<player.getSchoolBoard().getProfessors().size(); i++){
            System.out.print(player.getSchoolBoard().getProfessors().get(i).getColor() + "%n");
        }

        System.out.println("|Towers: " +
                            player.getSchoolBoard().getTowersNumber() +
                            "\n|--------------------------\n");
    }
}