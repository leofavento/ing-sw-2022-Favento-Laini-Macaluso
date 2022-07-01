package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.FxmlScenes;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Color;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    public Button buttonStop;


    private static final String blueStudent = "graphics/students/student_blue.png";
    private static final String greenStudent = "graphics/students/student_green.png";
    private static final String pinkStudent = "graphics/students/student_pink.png";
    private static final String redStudent = "graphics/students/student_red.png";
    private static final String yellowStudent = "graphics/students/student_yellow.png";
    private ArrayList<Color> availableStudents;

    private GUI gui;

    /**
     * method used to update the available movable students in the entrance
     * @param students the list of movable students
     */
    public void updateEntrance(ArrayList<Color> students){
        Button[] buttons = new Button[]{s0button, s1button, s2button, s3button, s4button, s5button, s6button, s7button, s8button};
        availableStudents = students;

        for(int i = 0; i < 9; i++){
            buttons[i].setVisible(false);
        }

        for(int i = 0; i < students.size(); i++){
            changeStudents(buttons[i], students.get(i));
            buttons[i].setVisible(true);
        }
        if (gui.getView().getActivatedCharacterEffect() && (gui.getView().getDashboard().getPlayedCharacter() != null)
                && (gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal()==6 ||
                gui.getView().getDashboard().getPlayedCharacter().getValue().ordinal()==9)){
            buttonStop.setVisible(true);
        }
    }

    /**
     * method used to handle the selection of student 0
     */
    public void selectStudent0(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(0));
    }

    /**
     * method used to handle the selection of student 1
     */
    public void selectStudent1(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(1));
    }

    /**
     * method used to handle the selection of student 2
     */
    public void selectStudent2(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(2));
    }

    /**
     * method used to handle the selection of student 3
     */
    public void selectStudent3(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(3));
    }

    /**
     * method used to handle the selection of student 4
     */
    public void selectStudent4(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(4));
    }

    /**
     * method used to handle the selection of student 5
     */
    public void selectStudent5(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(5));
    }

    /**
     * method used to handle the selection of student 6
     */
    public void selectStudent6(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(6));
    }

    /**
     * method used to handle the selection of student 7
     */
    public void selectStudent7(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(7));
    }

    /**
     * method used to handle the selection of student 8
     */
    public void selectStudent8(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(availableStudents.get(8));
    }


    /**
     * method used to handle the selection of the stop button
     */
    public void selectStop(){
        ((DashboardController) gui.getController(FxmlScenes.DASHBOARD.getPhase())).sendMovableStudents(null);
        buttonStop.setVisible(false);
    }

    /**
     * method used to update the images of the entrance students
     */
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
    public void nextPhase() {//unused method
    }
}
