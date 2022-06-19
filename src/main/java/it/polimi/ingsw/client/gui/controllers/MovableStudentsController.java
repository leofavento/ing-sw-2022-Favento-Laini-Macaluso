package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.FxmlScenes;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.fromClient.ChosenStudent;
import it.polimi.ingsw.messages.fromClient.ChosenWizard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.player.Entrance;
import it.polimi.ingsw.model.player.SchoolBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MovableStudentsController implements Controller{

    @FXML
    public Text title;

    @FXML
    public Button s0button;

    @FXML
    public Button s1button;

    @FXML
    public Button s2button;

    @FXML
    public Button s3button;

    @FXML
    public Button s4button;

    @FXML
    public Button s5button;

    @FXML
    public Button s6button;

    @FXML
    public Button s7button;

    @FXML
    public Button s8button;

    private final static String blueStudent = "graphics/students/student_blue.png";
    private final static String redStudent = "graphics/students/student_red.png";
    private final static String pinkStudent = "graphics/students/student_pink.png";
    private final static String greenStudent = "graphics/students/student_green.png";
    private final static String yellowStudent = "graphics/students/student_yellow.png";

    private GUI gui;


    public void updateEntrance(ArrayList<Color> students){
        Button[] buttons = new Button[]{s0button, s1button, s2button, s3button, s4button, s5button, s6button, s7button, s8button};

        for(int i = 0; i < 9; i++){
            buttons[i].setVisible(false);
        }

        for(int i = 0; i < students.size(); i++){
            changeStudents(buttons[i], students.get(i));
            buttons[i].setVisible(true);
        }


    }

    public void selectStudent0(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(0));
    }

    public void selectStudent1(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(1));
    }

    public void selectStudent2(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(2));
    }

    public void selectStudent3(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(3));
    }

    public void selectStudent4(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(4));
    }

    public void selectStudent5(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(5));
    }

    public void selectStudent6(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(6));
    }

    public void selectStudent7(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(7));
    }

    public void selectStudent8(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(8));
    }


    public void changeStudents(Button student, Color color){
        switch (color){
            case YELLOW -> student.setGraphic(new ImageView(new Image(yellowStudent)));
            case RED -> student.setGraphic(new ImageView(new Image(redStudent)));
            case PINK -> student.setGraphic(new ImageView(new Image(pinkStudent)));
            case BLUE -> student.setGraphic(new ImageView(new Image(blueStudent)));
            case GREEN -> student.setGraphic(new ImageView(new Image(greenStudent)));
        }
    }



    private Text error;
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }


    @Override
    public void error(String error) {
        this.error.setText(error);
        this.error.setVisible(true);
    }

    public void resetError() {
        error.setVisible(false);
    }

    @Override
    public void nextPhase() {

    }
}
