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

public class MovableStudentsController implements Controller{

    @FXML
    public Text title;

    @FXML
    public ImageView s0;

    @FXML
    public ImageView s1;

    @FXML
    public ImageView s2;

    @FXML
    public ImageView s3;

    @FXML
    public ImageView s4;

    @FXML
    public ImageView s5;

    @FXML
    public ImageView s6;

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


    private final static String blueStudent = "graphics/students/student_blue.png";
    private final static String redStudent = "graphics/students/student_red.png";
    private final static String pinkStudent = "graphics/students/student_pink.png";
    private final static String greenStudent = "graphics/students/student_green.png";
    private final static String yellowStudent = "graphics/students/student_yellow.png";

    private GUI gui;


    public void updateEntrance(Entrance e){
        ImageView[] students = new ImageView[]{s0, s1, s2, s3, s4, s5, s6};

        Button[] buttons = new Button[]{s0button, s1button, s2button, s3button, s4button, s5button, s6button};

        for(ImageView student : students){
            student.setVisible(false);
        }

        for(int i = 0; i < 7; i++){
            buttons[i].setVisible(false);
        }

        for(int i = 0; i < e.getStudents().size(); i++){
            changeStudents(students[i], e.getStudents().get(i));
            students[i].setVisible(true);

            buttons[i].setVisible(true);
        }


    }

    public void selectStudent0(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(0));
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).removeMovableStudentsScene();
    }

    public void selectStudent1(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(1));
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).removeMovableStudentsScene();
    }

    public void selectStudent2(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(2));
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).removeMovableStudentsScene();
    }

    public void selectStudent3(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(3));
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).removeMovableStudentsScene();
    }

    public void selectStudent4(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(4));
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).removeMovableStudentsScene();
    }

    public void selectStudent5(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(5));
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).removeMovableStudentsScene();
    }

    public void selectStudent6(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(gui.getView().getMovableStudents().get(6));
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).removeMovableStudentsScene();
    }



    public void changeStudents(ImageView student, Color color){
        switch (color){
            case YELLOW -> student.setImage(new Image(yellowStudent));
            case RED -> student.setImage(new Image(redStudent));
            case PINK -> student.setImage(new Image(pinkStudent));
            case BLUE -> student.setImage(new Image(blueStudent));
            case GREEN -> student.setImage(new Image(greenStudent));
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
