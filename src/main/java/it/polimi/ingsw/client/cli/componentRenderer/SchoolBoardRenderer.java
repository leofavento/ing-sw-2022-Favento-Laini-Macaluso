package it.polimi.ingsw.client.cli.componentRenderer;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.*;

public class SchoolBoardRenderer {


    public void renderSchoolBoard(SchoolBoard schoolBoard){
        System.out.println("|--------------------------\n" +
                "|---------ENTRANCE---------\n" +
                "|Green: " + "\n" + schoolBoard.getEntrance().getStudents().stream().filter(a->a==Color.GREEN).count() +
                "|Red: " + "\n" + schoolBoard.getEntrance().getStudents().stream().filter(a->a==Color.RED).count() +
                "|Yellow: " + "\n" + schoolBoard.getEntrance().getStudents().stream().filter(a->a==Color.YELLOW).count() +
                "|Pink: " + "\n" + schoolBoard.getEntrance().getStudents().stream().filter(a->a==Color.PINK).count() +
                "|Blue: " + "\n" + schoolBoard.getEntrance().getStudents().stream().filter(a->a==Color.BLUE).count() +
                "|--------------------------\n" +
                "|--------DINING ROOM------- " + "\n" +
                "|Green: " + "\n" + schoolBoard.getDiningRoom().getStudentsNumber(Color.GREEN) +
                "|Red: " + "\n" + schoolBoard.getDiningRoom().getStudentsNumber(Color.RED) +
                "|Yellow: " + "\n" + schoolBoard.getDiningRoom().getStudentsNumber(Color.YELLOW) +
                "|Pink: " + "\n" + schoolBoard.getDiningRoom().getStudentsNumber(Color.PINK) +
                "|Blue: " + "\n" + schoolBoard.getDiningRoom().getStudentsNumber(Color.BLUE) +
                "|--------------------------\n" +
                "|--------PROFESSORS-------- ");

        for(int i=0; i<schoolBoard.getProfessors().size(); i++){
            System.out.println("|" + schoolBoard.getProfessors().get(i).toString());
        }

        System.out.println("|--------------------------\n" +
                            "|----------TOWERS---------- " + "\n" +
                            "|" + schoolBoard.getTowersNumber() +
                            "|--------------------------\n");
    }
}