package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.*;
import it.polimi.ingsw.exceptions.AlreadyPlayedCharacterException;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.messages.fromServer.PlayedCharacter;
import it.polimi.ingsw.messages.fromServer.UpdateBoard;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.*;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * class used to control the characters effects
 */
public class CharacterController {
    Controller controller;
    Game game;
    VirtualView virtualview;

    public CharacterController(Controller c, Game g, VirtualView v) {
        this.controller = c;
        this.game = g;
        this.virtualview = v;
    }

    /**
     * method used to generate the game characters
     * @param dashboard the game Dashboard
     */
    public void generateCharacters(Dashboard dashboard) {
        ArrayList<CharacterEnum> allCharacters = new ArrayList<>();
        ArrayList<CharacterCard> gameCharacters = new ArrayList<>();

        Collections.addAll(allCharacters, CharacterEnum.values());
        Collections.shuffle(allCharacters);
        for (int i = 0; i < 3; i++) {
            gameCharacters.add(CharacterFactory.getCharacter(allCharacters.get(i)));
            setUpCharacter(gameCharacters.get(i), dashboard.getBag());
        }
        dashboard.setCharacters(gameCharacters);

    }

    /**
     * method used to fill the characters with students (from the bag)
     * only in character 1, 7 and 11
     * @param c the character card
     * @param bag the current bag
     */
    public void setUpCharacter(CharacterCard c, Bag bag) {
        switch (c.getValue()) {
            case Char1, Char7, Char11 -> c.setUp(bag);
        }
    }

    /**
     * method used to apply the effect of a character
     * @param c the selected character
     * @throws NotEnoughCoinsException exception thrown when a player does not have enough coins to play the selected character
     * @throws InvalidInputException exception thrown when the user input is not valid
     * @throws AlreadyPlayedCharacterException exception thrown when the selected character has already been played
     */
    public void applyEffect(CharacterEnum c) throws NotEnoughCoinsException, InvalidInputException, AlreadyPlayedCharacterException {
        CharacterCard selectedCard = Arrays
                .stream(game.getDashboard().getCharacters())
                .filter(card -> card.getValue() == c)
                .findAny()
                .orElse(null);
        if (selectedCard == null) {
            throw new InvalidInputException("The input is not a Character");
        }
        //Check if player has enough coins
        else if (!verifyCoins(selectedCard)) {
            throw new NotEnoughCoinsException("You don't have enough coins");
        } else if (game.getDashboard().getPlayedCharacter() != null) {
            throw new AlreadyPlayedCharacterException("You have already played a Character in this turn");
        } else {
            selectedCard.increaseCost();
            selectedCard.setActive();
            selectedCard.setUsedBy(game.getCurrentPlayer().getNickname());
            game.getDashboard().setPlayedCharacter(selectedCard);
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
            controller.notify(new PlayedCharacter(c, game.getCurrentPlayer().getNickname()));
            selectedCard.activate(this);
            controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
        }
    }

    /**
     * method used to activate character 1 effect
     * @param c the played character
     */
    public void activate(Char1 c) {
        //take 1 student from this card and place it on an Island
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar1(game, controller, previousState, c));
        controller.getState().execute();
    }

    /**
     * method used to activate character 2 effect
     * @param c the played character
     */
    public void activate(Char2 c) {
        //during this turn, you take control of any number of Professors even if you have
        //the same number of students as the player who currently controls them
    }

    /**
     * method used to activate character 3 effect
     * @param c the played character
     */
    public void activate(Char3 c) {
        //resolve an island as if Mother Nature has ended her movement here
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar3(game, controller, previousState, c));
        controller.getState().execute();
    }

    /**
     * method used to activate character 4 effect
     * @param c the played character
     */
    public void activate(Char4 c) {
        //you may move mother nature up to 2 additional islands
        game.getDashboard().setAdditionalMNMovements(2);
        controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
    }

    /**
     * method used to activate character 5 effect
     * @param c the played character
     */
    public void activate(Char5 c) {
        //place a No Entry tile on an Island of your choice
        try {
            c.useNoEntryTile();
            ResumableState previousState = (ResumableState) controller.getState();
            controller.setState(new EffectChar5(game, controller, previousState, c));
            controller.getState().execute();
        } catch (NoEntryTilesLeftException e) {
            controller.notify(ErrorMessage.ZERO_NO_ENTRY_TILES_LEFT);
        }
    }

    /**
     * method used to activate character 6 effect
     * @param c the played character
     */
    public void activate(Char6 c) {
        //when resolving a conquering on an island, towers do not count towards influence
        game.getDashboard().setDoNotCountTowers(true);
        controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
    }

    /**
     * method used to activate character 7 effect
     * @param c the played character
     */
    public void activate(Char7 c) {
        //You may take up to 3 students from this card and replace them with the same number of students from your entrance
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar7(game, controller, previousState, c));
        controller.getState().execute();
    }

    /**
     * method used to activate character 8 effect
     * @param c the played character
     */
    public void activate(Char8 c) {
        //During the influence calculation this turn, you count as having 2 more influence
        for (Island island : game.getDashboard().getIslands()) {
            island.setExtraInfluence(game.getCurrentPlayer(), 2);
        }
        controller.notify(new UpdateBoard(game.getDashboard(), game.getOnlinePlayers()));
    }

    /**
     * method used to activate character 9 effect
     * @param c the played character
     */
    public void activate(Char9 c) {
        //Choose a color of Student: during the influence calculation this turn, that color adds no influence
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar9(game, controller, previousState, c));
        controller.getState().execute();
    }

    /**
     * method used to activate character 10 effect
     * @param c the played character
     */
    public void activate(Char10 c) {
        //You may exchange up to 2 Students between your Entrance and your Dining Room
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar10(game, controller, previousState, c));
        controller.getState().execute();
    }

    /**
     * method used to activate character 11 effect
     * @param c the played character
     */
    public void activate(Char11 c) {
        //Take 1 student from this card and place it in your Dining Room
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar11(game, controller, previousState, c));
        controller.getState().execute();
    }

    /**
     * method used to activate character 12 effect
     * @param c the played character
     */
    public void activate(Char12 c) {
        //Choose a type of student: every player (including the current player) must return
        // 3 students of that type from their dining room to the bag
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar12(game, controller, previousState, c));
        controller.getState().execute();
    }


    /**
     * method used to verify if a player can play a given character with the current number of coins
      * @param c the selected character
     * @return true if a player can player the character, false if not
     */
    public boolean verifyCoins(CharacterCard c) {
        if ((game.getCurrentPlayer().getSchoolBoard().getCoins()) >= (c.getCost())) {
            game.getCurrentPlayer().getSchoolBoard().spendCoins(c.getCost());
            return true;
        } else {
            return false;
        }
    }
}
