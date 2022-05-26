package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.CharacterController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.ActionStep1;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.characters.*;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterGeneratorTest {

    @Test
    public void testFactory() {

        CharacterFactory characterFactory = new CharacterFactory();

        CharacterCard char1 = characterFactory.getCharacter(CharacterEnum.Char1);
        CharacterCard char2 = characterFactory.getCharacter(CharacterEnum.Char2);
        CharacterCard char3 = characterFactory.getCharacter(CharacterEnum.Char3);
        CharacterCard char4 = characterFactory.getCharacter(CharacterEnum.Char4);
        CharacterCard char5 = characterFactory.getCharacter(CharacterEnum.Char5);
        CharacterCard char6 = characterFactory.getCharacter(CharacterEnum.Char6);
        CharacterCard char7 = characterFactory.getCharacter(CharacterEnum.Char7);
        CharacterCard char8 = characterFactory.getCharacter(CharacterEnum.Char8);
        CharacterCard char9 = characterFactory.getCharacter(CharacterEnum.Char9);
        CharacterCard char10 = characterFactory.getCharacter(CharacterEnum.Char10);
        CharacterCard char11 = characterFactory.getCharacter(CharacterEnum.Char11);
        CharacterCard char12 = characterFactory.getCharacter(CharacterEnum.Char12);

        assertSame(char1.getValue(), CharacterEnum.Char1);
        assertSame(char2.getValue(), CharacterEnum.Char2);
        assertSame(char3.getValue(), CharacterEnum.Char3);
        assertSame(char4.getValue(), CharacterEnum.Char4);
        assertSame(char5.getValue(), CharacterEnum.Char5);
        assertSame(char6.getValue(), CharacterEnum.Char6);
        assertSame(char7.getValue(), CharacterEnum.Char7);
        assertSame(char8.getValue(), CharacterEnum.Char8);
        assertSame(char9.getValue(), CharacterEnum.Char9);
        assertSame(char10.getValue(), CharacterEnum.Char10);
        assertSame(char11.getValue(), CharacterEnum.Char11);
        assertSame(char12.getValue(), CharacterEnum.Char12);
    }
}
