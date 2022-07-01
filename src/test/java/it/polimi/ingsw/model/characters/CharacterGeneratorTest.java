package it.polimi.ingsw.model.characters;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterGeneratorTest {

    /**
     * Test used to verify the correct generation of every character
     */
    @Test
    public void testFactory() {

        CharacterCard char1 = CharacterFactory.getCharacter(CharacterEnum.Char1);
        CharacterCard char2 = CharacterFactory.getCharacter(CharacterEnum.Char2);
        CharacterCard char3 = CharacterFactory.getCharacter(CharacterEnum.Char3);
        CharacterCard char4 = CharacterFactory.getCharacter(CharacterEnum.Char4);
        CharacterCard char5 = CharacterFactory.getCharacter(CharacterEnum.Char5);
        CharacterCard char6 = CharacterFactory.getCharacter(CharacterEnum.Char6);
        CharacterCard char7 = CharacterFactory.getCharacter(CharacterEnum.Char7);
        CharacterCard char8 = CharacterFactory.getCharacter(CharacterEnum.Char8);
        CharacterCard char9 = CharacterFactory.getCharacter(CharacterEnum.Char9);
        CharacterCard char10 = CharacterFactory.getCharacter(CharacterEnum.Char10);
        CharacterCard char11 = CharacterFactory.getCharacter(CharacterEnum.Char11);
        CharacterCard char12 = CharacterFactory.getCharacter(CharacterEnum.Char12);

        assertSame(CharacterEnum.Char1, char1.getValue());
        assertSame(CharacterEnum.Char2, char2.getValue());
        assertSame(CharacterEnum.Char3, char3.getValue());
        assertSame(CharacterEnum.Char4, char4.getValue());
        assertSame(CharacterEnum.Char5, char5.getValue());
        assertSame(CharacterEnum.Char6, char6.getValue());
        assertSame(CharacterEnum.Char7, char7.getValue());
        assertSame(CharacterEnum.Char8, char8.getValue());
        assertSame(CharacterEnum.Char9, char9.getValue());
        assertSame(CharacterEnum.Char10, char10.getValue());
        assertSame(CharacterEnum.Char11, char11.getValue());
        assertSame(CharacterEnum.Char12, char12.getValue());
    }
}
