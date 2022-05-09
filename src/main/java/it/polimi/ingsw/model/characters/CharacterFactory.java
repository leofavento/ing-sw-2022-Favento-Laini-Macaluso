package it.polimi.ingsw.model.characters;

public class CharacterFactory {

    public CharacterFactory(){}

    public CharacterCard getCharacter(CharacterEnum type){
        CharacterCard ch= null;
        switch (type){
            case Char1:
                ch=new Char1();
                break;
            case Char2:
                ch=new Char2();
                break;
            case Char3:
                ch=new Char3();
                break;
            case Char4:
                ch=new Char4();
                break;
            case Char5:
                ch=new Char5();
                break;
            case Char6:
                ch=new Char6();
                break;
            case Char7:
                ch=new Char7();
                break;
            case Char8:
                ch=new Char8();
                break;
            case Char9:
                ch=new Char9();
                break;
            case Char10:
                ch=new Char10();
            case Char11:
                ch=new Char11();
                break;
            case Char12:
                ch=new Char12();
                break;
            }
            return ch;
        }

    }
