package it.polimi.ingsw.client.cli.componentRenderer;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.characters.Char2;

public class ActiveEffectsRenderer {

    /**
     * method used to print to the command line the active effects
     * @param dashboard the game dashboard
     */
    public static void printActiveEffects(Dashboard dashboard) {
        System.out.println("|-----------------------------");
        System.out.println("| Active turn effects: ");
        if (dashboard.getPlayedCharacter() instanceof Char2) {
            System.out.println("You take control of Professors even if you have the same number of Students as the current owner.");
        }
        if (dashboard.getDoNotCountTowers()) {
            System.out.println("Towers do not count towards influence.");
        }
        if (dashboard.getIslands().get(0).hasExtraInfluence()) {
            System.out.println("You count as having 2 more influence.");
        }
        if (dashboard.getDoNotCountColor() != null) {
            System.out.println(dashboard.getDoNotCountColor() + " Students add no influence.");
        }
        System.out.println("|-----------------------------");
    }
}
