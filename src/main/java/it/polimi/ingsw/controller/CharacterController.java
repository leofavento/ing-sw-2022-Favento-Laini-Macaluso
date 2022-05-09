package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.CharacterEnum;
import it.polimi.ingsw.model.characters.CharacterFactory;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterController {
    public CharacterController(){}

    public void generateCharacters(Dashboard dashboard) {
        ArrayList<CharacterEnum> allCharacters = new ArrayList<>();
        CharacterFactory factory = new CharacterFactory();
        ArrayList<CharacterCard> gameCharacters = new ArrayList<>();

        Collections.addAll(allCharacters, CharacterEnum.values());
        Collections.shuffle(allCharacters);
        for (int i = 0; i < 3; i++) {
            gameCharacters.add(factory.getCharacter(allCharacters.get(i)));
        }
        dashboard.setCharacters(gameCharacters);
    }


        /*for (CharacterCard c : dashboard.getCharacters()) {
            if (c instanceof Type2) {
                switch((Type2) c) {
                    case CARD1:
                    case CARD11:
                        IntStream.range(0,4).
                                forEach(i -> ((Type2) c).addStudent(dashboard.getBag().drawStudent()));
                        break;
                    case CARD7:
                        IntStream.range(0,6).
                                forEach(i -> ((Type2) c).addStudent(dashboard.getBag().drawStudent()));
                        break;
                }
            }
        }*/


    /*public void applyEffect(Type1 character, Object destination) {

    }*/
}
