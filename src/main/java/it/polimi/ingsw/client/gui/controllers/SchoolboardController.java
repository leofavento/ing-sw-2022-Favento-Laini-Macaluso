package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Entrance;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SchoolboardController implements Controller {
    @FXML private Button assistant1;
    @FXML private Button assistant2;
    @FXML private Button assistant3;
    @FXML private Button assistant4;
    @FXML private Button assistant5;
    @FXML private Button assistant6;
    @FXML private Button assistant7;
    @FXML private Button assistant8;
    @FXML private Button assistant9;
    @FXML private Button assistant10;
    @FXML private ImageView green1;
    @FXML private ImageView green2;
    @FXML private ImageView green3;
    @FXML private ImageView green4;
    @FXML private ImageView green5;
    @FXML private ImageView green6;
    @FXML private ImageView green7;
    @FXML private ImageView green8;
    @FXML private ImageView green9;
    @FXML private ImageView green10;
    @FXML private ImageView red1;
    @FXML private ImageView red2;
    @FXML private ImageView red3;
    @FXML private ImageView red4;
    @FXML private ImageView red5;
    @FXML private ImageView red6;
    @FXML private ImageView red7;
    @FXML private ImageView red8;
    @FXML private ImageView red9;
    @FXML private ImageView red10;
    @FXML private ImageView yellow1;
    @FXML private ImageView yellow2;
    @FXML private ImageView yellow3;
    @FXML private ImageView yellow4;
    @FXML private ImageView yellow5;
    @FXML private ImageView yellow6;
    @FXML private ImageView yellow7;
    @FXML private ImageView yellow8;
    @FXML private ImageView yellow9;
    @FXML private ImageView yellow10;
    @FXML private ImageView pink1;
    @FXML private ImageView pink2;
    @FXML private ImageView pink3;
    @FXML private ImageView pink4;
    @FXML private ImageView pink5;
    @FXML private ImageView pink6;
    @FXML private ImageView pink7;
    @FXML private ImageView pink8;
    @FXML private ImageView pink9;
    @FXML private ImageView pink10;
    @FXML private ImageView blue1;
    @FXML private ImageView blue2;
    @FXML private ImageView blue3;
    @FXML private ImageView blue4;
    @FXML private ImageView blue5;
    @FXML private ImageView blue6;
    @FXML private ImageView blue7;
    @FXML private ImageView blue8;
    @FXML private ImageView blue9;
    @FXML private ImageView blue10;
    @FXML private ImageView tower1;
    @FXML private ImageView tower2;
    @FXML private ImageView tower3;
    @FXML private ImageView tower4;
    @FXML private ImageView tower5;
    @FXML private ImageView tower6;
    @FXML private ImageView tower7;
    @FXML private ImageView tower8;
    @FXML private ImageView greenProfessor;
    @FXML private ImageView redProfessor;
    @FXML private ImageView yellowProfessor;
    @FXML private ImageView pinkProfessor;
    @FXML private ImageView blueProfessor;
    @FXML private ImageView entrance1;
    @FXML private ImageView entrance2;
    @FXML private ImageView entrance3;
    @FXML private ImageView entrance4;
    @FXML private ImageView entrance5;
    @FXML private ImageView entrance6;
    @FXML private ImageView entrance7;
    @FXML private ImageView entrance8;
    @FXML private ImageView entrance9;
    @FXML private ImageView coinsIcon;
    @FXML private Text coins;

    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void error(String error) {

    }

    @Override
    public void nextPhase() {

    }

    public void resetAssistantsButtons() {
        assistant1.setDisable(true);
        assistant2.setDisable(true);
        assistant3.setDisable(true);
        assistant4.setDisable(true);
        assistant5.setDisable(true);
        assistant6.setDisable(true);
        assistant7.setDisable(true);
        assistant8.setDisable(true);
        assistant9.setDisable(true);
        assistant10.setDisable(true);
    }

    public void requestAssistant() {
        ArrayList<Assistant> availableAssistants = gui.getView().getAvailableAssistants();

        assistant1.setDisable(!availableAssistants.contains(Assistant.TIGER));
        assistant2.setDisable(!availableAssistants.contains(Assistant.OSTRICH));
        assistant3.setDisable(!availableAssistants.contains(Assistant.ARCHER_CAT));
        assistant4.setDisable(!availableAssistants.contains(Assistant.EAGLE));
        assistant5.setDisable(!availableAssistants.contains(Assistant.FOX));
        assistant6.setDisable(!availableAssistants.contains(Assistant.SNAKE));
        assistant7.setDisable(!availableAssistants.contains(Assistant.OCTOPUS));
        assistant8.setDisable(!availableAssistants.contains(Assistant.DOG));
        assistant9.setDisable(!availableAssistants.contains(Assistant.ELEPHANT));
        assistant10.setDisable(!availableAssistants.contains(Assistant.TURTLE));

        //TODO set some text telling to choose an assistant
    }

    public void update(Player player) {
        updateDining(player.getSchoolBoard().getDiningRoom());
        updateTowers(player.getSchoolBoard());
        updateProfessors(player.getSchoolBoard());
        updateEntrance(player.getSchoolBoard().getEntrance());
        if (gui.getView().isExpertMode()) {
            coinsIcon.setVisible(true);
            coins.setVisible(true);
            coins.setText(String.format("%d", player.getSchoolBoard().getCoins()));
        } else {
            coinsIcon.setVisible(false);
            coins.setVisible(false);
        }
    }

    private void updateDining(DiningRoom diningRoom) {
        ImageView[] greenStudents = new ImageView[]{green1,
                green2, green3, green4, green5, green6, green7, green8, green9, green10};
        setDiningColor(greenStudents, diningRoom.getStudentsNumber(Color.GREEN));

        ImageView[] redStudents = new ImageView[]{red1,
                red2, red3, red4, red5, red6, red7, red8, red9, red10};
        setDiningColor(redStudents, diningRoom.getStudentsNumber(Color.RED));

        ImageView[] yellowStudents = new ImageView[]{yellow1,
                yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10};
        setDiningColor(yellowStudents, diningRoom.getStudentsNumber(Color.YELLOW));

        ImageView[] pinkStudents = new ImageView[]{pink1,
                pink2, pink3, pink4, pink5, pink6, pink7, pink8, pink9, pink10};
        setDiningColor(pinkStudents, diningRoom.getStudentsNumber(Color.PINK));

        ImageView[] blueStudents = new ImageView[]{blue1,
                blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10};
        setDiningColor(blueStudents, diningRoom.getStudentsNumber(Color.BLUE));
    }

    private void setDiningColor(ImageView[] students, int number) {
        for (int i = 0; i < 10; i++) {
            students[i].setVisible(number > i);
        }
    }

    private void updateTowers(SchoolBoard schoolBoard) {
        ImageView[] towers = new ImageView[]{tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8};
        switch (schoolBoard.getTowerColor()) {
            case GREY -> {
                for (ImageView tower : towers) {
                    tower.setImage(new Image("graphics/towers/greytower.png"));
                }
            }
            case BLACK -> {
                for (ImageView tower : towers) {
                    tower.setImage(new Image("graphics/towers/blacktower.png"));
                }
            }
            case WHITE -> {
                for (ImageView tower : towers) {
                    tower.setImage(new Image("graphics/towers/whitetower.png"));
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            towers[i].setVisible(schoolBoard.getTowersNumber() > i);
        }
    }

    private void updateProfessors(SchoolBoard schoolBoard) {
        greenProfessor.setVisible(schoolBoard.getProfessors().stream()
                .anyMatch(professor -> professor.getColor() == Color.GREEN));
        redProfessor.setVisible(schoolBoard.getProfessors().stream()
                .anyMatch(professor -> professor.getColor() == Color.RED));
        yellowProfessor.setVisible(schoolBoard.getProfessors().stream()
                .anyMatch(professor -> professor.getColor() == Color.YELLOW));
        pinkProfessor.setVisible(schoolBoard.getProfessors().stream()
                .anyMatch(professor -> professor.getColor() == Color.PINK));
        blueProfessor.setVisible(schoolBoard.getProfessors().stream()
                .anyMatch(professor -> professor.getColor() == Color.BLUE));
    }

    private void updateEntrance(Entrance entrance) {
        ImageView[] students = new ImageView[]{entrance1, entrance2,
                entrance3, entrance4, entrance5, entrance6, entrance7, entrance8, entrance9};
        for (ImageView student : students) {
            student.setVisible(false);
        }
        for (int i = 0; i < entrance.getStudents().size(); i++) {
            changeStudent(students[i], entrance.getStudents().get(i));
            students[i].setVisible(true);
        }
    }

    private void changeStudent(ImageView student, Color color) {
        switch(color) {
            case GREEN -> student.setImage(new Image("graphics/students/student_green.png"));
            case RED -> student.setImage(new Image("graphics/students/student_red.png"));
            case YELLOW -> student.setImage(new Image("graphics/students/student_yellow.png"));
            case PINK -> student.setImage(new Image("graphics/students/student_pink.png"));
            case BLUE -> student.setImage(new Image("graphics/students/student_blue.png"));
        }
    }
}
