package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

/**
 * this Character allow the player to pick one student from this card and place it on a selected Island
 */
public class Char1 extends CharacterCard {

    public Char1() {
        value = CharacterEnum.valueOf(("Char1"));
        cost = 1;
        students = new ArrayList<>();
    }


    /**
     * method used to add 4 students on this card in the setup phase
     * @param bag the current bag
     */
    @Override
    public void setUp(Bag bag) {
        while (students.size() < 4) {
            students.add(bag.drawStudent());
        }
    }

    /**
     * method used to add back a student to this card
     * @param c the student
     */
    @Override
    public void addStudent(Color c) {
        this.students.add(c);
    }

    /**
     * method used to remove a selected student from this card
     * @param c the selected student
     */
    @Override
    public void removeStudent(Color c) {
        this.students.remove(c);
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
