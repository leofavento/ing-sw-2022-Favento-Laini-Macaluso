package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

/**
 * this Character allows the player to take 1 student from this card and to place it in his Dining Room
 */
public class Char11 extends CharacterCard {

    public Char11() {
        value = CharacterEnum.valueOf(("Char11"));
        cost = 2;
        students = new ArrayList<>();
        description = "Take 1 Student from this card and place it in your Dining Room. " +
                "Then, draw a new Student from the Bag and place it on this card.";
    }

    @Override
    public void setUp(Bag bag) {
        while (students.size() < 4) {
            students.add(bag.drawStudent());
        }
    }

    @Override
    public void addStudent(Color c) {
        this.students.add(c);
    }

    @Override
    public void activate(CharacterController controller){
        controller.activate(this);
    }
}
