package it.polimi.ingsw.client.cli.componentRenderer;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.Char5;

public class CharacterRenderer {
    /**
     * This method prints all the informations about an array of CharacterCard
     *
     * @param cards the array of CharacterCard
     */
    public static void renderAllCharacters(CharacterCard[] cards) {
        for (CharacterCard card : cards) {
            characterRenderer(card);
        }
        System.out.println("|-----------------------------");
    }

    /**
     * This method prints all the informations about a specific CharacterCard
     * @param card the requested card
     */
    public static void characterRenderer(CharacterCard card) {
        System.out.println("|-----------------------------");
        System.out.println(card.getValue());
        System.out.println("Effect: " + card.getDescription());
        System.out.println("Cost: " + card.getCost());
        if (card.getActive()) {
            System.out.println("Used by " + card.getUsedBy());
        }
        if (card.getStudents() != null) {
            System.out.print("Students: ");
            for (Color student : card.getStudents()) {
                System.out.print(student + " ");
            }
            System.out.println();
        }
        if (card instanceof Char5) {
            System.out.println("No Entry tiles: " + ((Char5) card).getNoEntryTiles());
        }
    }
}
