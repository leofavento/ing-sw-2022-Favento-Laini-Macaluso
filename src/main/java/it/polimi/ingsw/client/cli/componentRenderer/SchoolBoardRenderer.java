package it.polimi.ingsw.client.cli.componentRenderer;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.*;

public class SchoolBoardRenderer {

    /**
     * This method prints to command line the SchoolBoard of a requested Player, containing all the details
     * about the Entrance, the Dining Room and the Professors
     * @param player SchoolBoard owner
     */
    public static  void renderSchoolBoard(Player player){
        System.out.println("|--------------------------\n" +
                player.getNickname() + "\n" +
                "|---------ENTRANCE---------\n" +
                "|Green: " + "\n" + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.GREEN).count() +
                "|Red: " + "\n" + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.RED).count() +
                "|Yellow: " + "\n" + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.YELLOW).count() +
                "|Pink: " + "\n" + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.PINK).count() +
                "|Blue: " + "\n" + player.getSchoolBoard().getEntrance().getStudents().stream().filter(a->a==Color.BLUE).count() +
                "|--------------------------\n" +
                "|--------DINING ROOM------- " + "\n" +
                "|Green: " + "\n" + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.GREEN) +
                "|Red: " + "\n" + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.RED) +
                "|Yellow: " + "\n" + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.YELLOW) +
                "|Pink: " + "\n" + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.PINK) +
                "|Blue: " + "\n" + player.getSchoolBoard().getDiningRoom().getStudentsNumber(Color.BLUE) +
                "|--------------------------\n" +
                "|--------PROFESSORS-------- ");

        for(int i=0; i<player.getSchoolBoard().getProfessors().size(); i++){
            System.out.println("|" + player.getSchoolBoard().getProfessors().get(i).toString());
        }

        System.out.println("|--------------------------\n" +
                            "|----------TOWERS---------- " + "\n" +
                            "|" + player.getSchoolBoard().getTowersNumber() +
                            "|--------------------------\n");
    }
}