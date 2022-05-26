package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

/**
 * this Character allows the player to take up to 3 students from this card and replace them with the same number from his entrance
 */
public class Char7 extends CharacterCard {

    public Char7() {
        value = CharacterEnum.valueOf(("Char7"));
        students = new ArrayList<>();
        cost = 1;
    }

    /**
     * method used to add 6 students to this card in the setup phase
     * @param bag the current bag
     */
    @Override
    public void setUp(Bag bag) {
        while (students.size() < 6) {
            students.add(bag.drawStudent());
        }
    }

    @Override
    public void addStudent(Color c) {
        this.students.add(c);
    }

    @Override
    public void removeStudent(Color c) {
        this.students.remove(c);
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
