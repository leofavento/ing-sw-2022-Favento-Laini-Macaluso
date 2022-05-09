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

    public void setUpCharacter(CharacterCard c){
        //TODO
    }

    public void applyEffect(CharacterCard c){
        switch(c.getValue()){
            case Char1:
                //TODO

        }

    }
}
