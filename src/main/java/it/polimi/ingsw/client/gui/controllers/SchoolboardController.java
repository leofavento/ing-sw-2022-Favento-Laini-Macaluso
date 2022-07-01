package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.fromClient.PlayAssistant;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Entrance;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Map;

public class SchoolboardController implements Controller {
    @FXML private ImageView assistantImage1;
    @FXML private ImageView assistantImage2;
    @FXML private ImageView assistantImage3;
    @FXML private ImageView assistantImage4;
    @FXML private ImageView assistantImage5;
    @FXML private ImageView assistantImage6;
    @FXML private ImageView assistantImage7;
    @FXML private ImageView assistantImage8;
    @FXML private ImageView assistantImage9;
    @FXML private ImageView assistantImage10;
    @FXML private Text played1;
    @FXML private Text played2;
    @FXML private Text played3;
    @FXML private Text played4;
    @FXML private Text played5;
    @FXML private Text played6;
    @FXML private Text played7;
    @FXML private Text played8;
    @FXML private Text played9;
    @FXML private Text played10;
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
    @FXML private Label teamColor;

    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void error(String error) {//unused method
    }

    @Override
    public void nextPhase() {//unused method
    }

    /**
     * method used to remove the buttons of all the assistants (setting them as insvisible)
     */
    public void removeAssistantButtons() {
        assistant1.setVisible(false);
        assistant2.setVisible(false);
        assistant3.setVisible(false);
        assistant4.setVisible(false);
        assistant5.setVisible(false);
        assistant6.setVisible(false);
        assistant7.setVisible(false);
        assistant8.setVisible(false);
        assistant9.setVisible(false);
        assistant10.setVisible(false);
    }

    /**
     * method used to disable the buttons of all the assistants
     */
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

    /**
     * method used to remove the played assistants
     */
    public void removePlayedAssistants() {
        played1.setVisible(false);
        played2.setVisible(false);
        played3.setVisible(false);
        played4.setVisible(false);
        played5.setVisible(false);
        played6.setVisible(false);
        played7.setVisible(false);
        played8.setVisible(false);
        played9.setVisible(false);
        played10.setVisible(false);
    }

    /**
     * method used to show the played assistants
     */
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

        Map<String, Assistant> playedAssistants = gui.getView().getPlayedAssistants();
        for (String player : playedAssistants.keySet()) {
            switch (playedAssistants.get(player)) {
                case TIGER -> {
                    played1.setText(player + " played this assistant");
                    played1.setVisible(true);
                }
                case OSTRICH -> {
                    played2.setText(player + " played this assistant");
                    played2.setVisible(true);
                }
                case ARCHER_CAT -> {
                    played3.setText(player + " played this assistant");
                    played3.setVisible(true);
                }
                case EAGLE -> {
                    played4.setText(player + " played this assistant");
                    played4.setVisible(true);
                }
                case FOX -> {
                    played5.setText(player + " played this assistant");
                    played5.setVisible(true);
                }
                case SNAKE -> {
                    played6.setText(player + " played this assistant");
                    played6.setVisible(true);
                }
                case OCTOPUS -> {
                    played7.setText(player + " played this assistant");
                    played7.setVisible(true);
                }
                case DOG -> {
                    played8.setText(player + " played this assistant");
                    played8.setVisible(true);
                }
                case ELEPHANT -> {
                    played9.setText(player + " played this assistant");
                    played9.setVisible(true);
                }
                case TURTLE -> {
                    played10.setText(player + " played this assistant");
                    played10.setVisible(true);
                }
            }
        }
    }

    /**
     * method used to show informations about coins (only in expert mode) in the player's SchoolBoard
     * @param player the desired player
     */
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

    /**
     * method used to update all infos about the dining room
     * @param diningRoom a player's dining room
     */
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

    /**
     * method used to show properly the images of the dining room students
     * @param students the students currently in the dining room
     * @param number the number of students to show
     */
    private void setDiningColor(ImageView[] students, int number) {
        for (int i = 0; i < 10; i++) {
            students[i].setVisible(number > i);
        }
    }

    /**
     * method used to update the towers currently in a player's SchoolBoard
     * @param schoolBoard a player's SchoolBoard
     */
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

    /**
     * method used to update the professors currently in a player's SchoolBoard
     * @param schoolBoard a player's SchoolBoard
     */
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

    /**
     * method used to update the images of the students currently in a player's entrance
     * @param entrance a player's entrance
     */
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

    /**
     * method used to set the images of the students properly, based on their colors
     * @param student the selected student
     * @param color the student's color
     */
    private void changeStudent(ImageView student, Color color) {
        switch(color) {
            case GREEN -> student.setImage(new Image("graphics/students/student_green.png"));
            case RED -> student.setImage(new Image("graphics/students/student_red.png"));
            case YELLOW -> student.setImage(new Image("graphics/students/student_yellow.png"));
            case PINK -> student.setImage(new Image("graphics/students/student_pink.png"));
            case BLUE -> student.setImage(new Image("graphics/students/student_blue.png"));
        }
    }

    /**
     * method used to handle the selection of assistant 1
     */
    public void playAssistant1() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.TIGER));
    }

    /**
     * method used to handle the selection of assistant 2
     */
    public void playAssistant2() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.OSTRICH));
    }

    /**
     * method used to handle the selection of assistant 3
     */
    public void playAssistant3() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.ARCHER_CAT));
    }

    /**
     * method used to handle the selection of assistant 4
     */
    public void playAssistant4() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.EAGLE));
    }

    /**
     * method used to handle the selection of assistant 5
     */
    public void playAssistant5() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.FOX));
    }

    /**
     * method used to handle the selection of assistant 6
     */
    public void playAssistant6() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.SNAKE));
    }

    /**
     * method used to handle the selection of assistant 7
     */
    public void playAssistant7() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.OCTOPUS));
    }

    /**
     * method used to handle the selection of assistant 8
     */
    public void playAssistant8() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.DOG));
    }

    /**
     * method used to handle the selection of assistant 9
     */
    public void playAssistant9() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.ELEPHANT));
    }

    /**
     * method used to handle the selection of assistant 10
     */
    public void playAssistant10() {
        gui.getClient().sendMessage(new PlayAssistant(Assistant.TURTLE));
    }

    /**
     * method used to set the right color of a player's team
     * @param player the selected player
     */
    public void setTeamColor(Player player){
        switch (player.getSchoolBoard().getTowerColor()){
            case GREY -> {
                teamColor.setText("GREY");
                teamColor.setStyle("-fx-text-fill: grey;");
            }
            case BLACK -> {
                teamColor.setText("BLACK");
                teamColor.setStyle("-fx-text-fill: black;");
            }
            case WHITE -> {
                teamColor.setText("WHITE");
                teamColor.setStyle("-fx-text-fill: white;");
            }
        }
    }

    /**
     * method used to show if an assistant has already been played
     * @param assistant the selected assistant
     */
    public void showPlayed(Assistant assistant) {
        removePlayedAssistants();
        switch (assistant) {
            case TIGER -> {
                played1.setText("PLAYED");
                played1.setVisible(true);
            }
            case OSTRICH -> {
                played2.setText("PLAYED");
                played2.setVisible(true);
            }
            case ARCHER_CAT -> {
                played3.setText("PLAYED");
                played3.setVisible(true);
            }
            case EAGLE -> {
                played4.setText("PLAYED");
                played4.setVisible(true);
            }
            case FOX -> {
                played5.setText("PLAYED");
                played5.setVisible(true);
            }
            case SNAKE -> {
                played6.setText("PLAYED");
                played6.setVisible(true);
            }
            case OCTOPUS -> {
                played7.setText("PLAYED");
                played7.setVisible(true);
            }
            case DOG -> {
                played8.setText("PLAYED");
                played8.setVisible(true);
            }
            case ELEPHANT -> {
                played9.setText("PLAYED");
                played9.setVisible(true);
            }
            case TURTLE -> {
                played10.setText("PLAYED");
                played10.setVisible(true);
            }
        }
    }

    public void removeAssistantImage() {
        assistantImage1.setVisible(assistantImage1.isVisible() && !played1.isVisible());
        assistantImage2.setVisible(assistantImage2.isVisible() && !played2.isVisible());
        assistantImage3.setVisible(assistantImage3.isVisible() && !played3.isVisible());
        assistantImage4.setVisible(assistantImage4.isVisible() && !played4.isVisible());
        assistantImage5.setVisible(assistantImage5.isVisible() && !played5.isVisible());
        assistantImage6.setVisible(assistantImage6.isVisible() && !played6.isVisible());
        assistantImage7.setVisible(assistantImage7.isVisible() && !played7.isVisible());
        assistantImage8.setVisible(assistantImage8.isVisible() && !played8.isVisible());
        assistantImage9.setVisible(assistantImage9.isVisible() && !played9.isVisible());
        assistantImage10.setVisible(assistantImage10.isVisible() && !played10.isVisible());
    }
}
