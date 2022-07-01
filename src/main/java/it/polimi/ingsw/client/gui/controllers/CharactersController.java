package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.fromClient.UseCharacterEffect;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.characters.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CharactersController implements Controller{
    @FXML private Button button;
    @FXML private Text description;
    @FXML private Text activeChar;
    @FXML private ImageView noEntry1;
    @FXML private ImageView noEntry2;
    @FXML private ImageView noEntry3;
    @FXML private ImageView noEntry4;
    @FXML private ImageView character;
    @FXML private Text cost;
    @FXML private ImageView student1;
    @FXML private ImageView student2;
    @FXML private ImageView student3;
    @FXML private ImageView student4;
    @FXML private ImageView student5;
    @FXML private ImageView student6;
    private CharacterEnum charEnum;
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
     * method used to disable a button
     * @param disable true if the button has to be disabled
     */
    public void disableButton(boolean disable) {
        button.setDisable(disable);
    }

    /**
     * method used to update the infos about a given character card
     * @param characterCard the character card to update
     */
    public void update(CharacterCard characterCard) {
        charEnum = characterCard.getValue();
        setCharacter();
        cost.setText("Cost: " + characterCard.getCost());
        description.setText(characterCard.getDescription());
        activeChar.setVisible(false);
        student1.setVisible(false);
        student2.setVisible(false);
        student3.setVisible(false);
        student4.setVisible(false);
        student5.setVisible(false);
        student6.setVisible(false);
        noEntry1.setVisible(false);
        noEntry2.setVisible(false);
        noEntry3.setVisible(false);
        noEntry4.setVisible(false);
        activeChar.setVisible(characterCard.getActive());

        if (characterCard instanceof Char1 || characterCard instanceof Char7
                || characterCard instanceof Char11) {
            setStudent(student1, characterCard.getStudents().get(0));
            setStudent(student2, characterCard.getStudents().get(1));
            setStudent(student3, characterCard.getStudents().get(2));
            setStudent(student4, characterCard.getStudents().get(3));
        }
        if (characterCard instanceof Char7) {
            setStudent(student5, characterCard.getStudents().get(4));
            setStudent(student6, characterCard.getStudents().get(5));
        }
        if (characterCard instanceof Char5) {
            noEntry1.setVisible(((Char5) characterCard).getNoEntryTiles() >= 1);
            noEntry2.setVisible(((Char5) characterCard).getNoEntryTiles() >= 2);
            noEntry3.setVisible(((Char5) characterCard).getNoEntryTiles() >= 3);
            noEntry4.setVisible(((Char5) characterCard).getNoEntryTiles() >= 4);
        }
    }

    /**
     * method used to set a student image, given its color
     * @param image the image object to update
     * @param student the color of the student
     */
    private void setStudent(ImageView image, Color student) {
        switch(student) {
            case YELLOW -> image.setImage(new Image("graphics/students/student_yellow.png"));
            case RED -> image.setImage(new Image("graphics/students/student_red.png"));
            case GREEN -> image.setImage(new Image("graphics/students/student_green.png"));
            case PINK -> image.setImage(new Image("graphics/students/student_pink.png"));
            case BLUE -> image.setImage(new Image("graphics/students/student_blue.png"));
        }
        image.setVisible(true);
    }

    /**
     * method used to set the right image to show for every character
     */
    private void setCharacter() {
        switch (charEnum) {
            case Char1 -> character.setImage(new Image("graphics/characters/character1.jpg"));
            case Char2 -> character.setImage(new Image("graphics/characters/character2.jpg"));
            case Char3 -> character.setImage(new Image("graphics/characters/character3.jpg"));
            case Char4 -> character.setImage(new Image("graphics/characters/character4.jpg"));
            case Char5 -> character.setImage(new Image("graphics/characters/character5.jpg"));
            case Char6 -> character.setImage(new Image("graphics/characters/character6.jpg"));
            case Char7 -> character.setImage(new Image("graphics/characters/character7.jpg"));
            case Char8 -> character.setImage(new Image("graphics/characters/character8.jpg"));
            case Char9 -> character.setImage(new Image("graphics/characters/character9.jpg"));
            case Char10 -> character.setImage(new Image("graphics/characters/character10.jpg"));
            case Char11 -> character.setImage(new Image("graphics/characters/character11.jpg"));
            case Char12 -> character.setImage(new Image("graphics/characters/character12.jpg"));
        }
    }

    /**
     * method used to handle the activation of a character
     */
    public void activate() {
        gui.getClient().sendMessage(new UseCharacterEffect(charEnum));
    }
}
