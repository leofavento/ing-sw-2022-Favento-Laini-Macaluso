package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.*;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.NoEntryTilesLeftException;
import it.polimi.ingsw.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.*;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterController {
    Controller controller;
    Game game;
    VirtualView virtualview;

    public CharacterController(Controller c, Game g, VirtualView v) {
        this.controller = c;
        this.game = g;
        this.virtualview = v;
    }

    public void generateCharacters(Dashboard dashboard) {
        ArrayList<CharacterEnum> allCharacters = new ArrayList<>();
        CharacterFactory factory = new CharacterFactory();
        ArrayList<CharacterCard> gameCharacters = new ArrayList<>();

        Collections.addAll(allCharacters, CharacterEnum.values());
        Collections.shuffle(allCharacters);
        for (int i = 0; i < 3; i++) {
            gameCharacters.add(factory.getCharacter(allCharacters.get(i)));
            setUpCharacter(gameCharacters.get(i), dashboard.getBag());
        }
        dashboard.setCharacters(gameCharacters);

    }

    public void setUpCharacter(CharacterCard c, Bag bag) {
        switch (c.getValue()) {
            case Char1:
            case Char7:
            case Char11:
                c.setUp(bag);
                break;
        }
    }

    public void applyEffect(CharacterCard c) throws NotEnoughCoinsException, InvalidInputException {
        if (c == null) throw new InvalidInputException("The input is not a Character");
        //Check if player has enough coins
        if (!verifyCoins(c)) {
            throw new NotEnoughCoinsException("You don't have enough coins");
        } else {
            c.increaseCost();
            c.setActive();
            c.setUsedBy(game.getCurrentPlayer().getNickname());
            c.activate(this);
        }
    }

    public void activate(Char1 c) {
        //take 1 student from this card and place it on an Island
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar1(game, controller, previousState, c));
        controller.getState().execute();
    }

    public void activate(Char2 c) {
        //during this turn, you take control of any number of Professors even if you have
        //the same number of students as the player who currently controls them
        for (int i = 0; i < 5; i++) {
            if (!(game.getDashboard().getProfessors()[i].getOwner() == game.getCurrentPlayer())) {
                Professor p = game.getDashboard().getProfessors()[i];
                int temp;
                Color color = game.getDashboard().getProfessors()[i].getColor();
                temp = game.getDashboard().getProfessors()[i].getOwner().getSchoolBoard().getDiningRoom().getStudentsNumber(color);
                if (temp == game.getCurrentPlayer().getSchoolBoard().getDiningRoom().getStudentsNumber(color)) {
                    game.getCurrentPlayer().getSchoolBoard().addProfessor(p);
                    game.getDashboard().getProfessors()[i].getOwner().getSchoolBoard().removeProfessor(p);
                }
            }
        }
    }

    public void activate(Char3 c) {
        //resolve an island as if Mother Nature has ended her movement here
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar3(game, controller, previousState, c));
        controller.getState().execute();
    }

    public void activate(Char4 c) {
        //you may move mother nature up to 2 additional islands
        game.getDashboard().setAdditionalMNMovements(2);
    }

    public void activate(Char5 c) {
        //place a No Entry tile on an Island of your choice
        try {
            c.useNoEntryTile();
        } catch (NoEntryTilesLeftException e) {
            controller.notify(ErrorMessage.ZERO_NO_ENTRY_TILES_LEFT);
        }

        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar5(game, controller, previousState, c));
        controller.getState().execute();
    }

    public void activate(Char6 c) {
        //when resolving a conquering on an island, towers do not count towards influence
        game.getDashboard().setDoNotCountTowers(true);
    }

    public void activate(Char7 c) {
        //You may take up to 3 students from this card and replace them with the same number of students from your entrance
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar7(game, controller, previousState, c));
        controller.getState().execute();
    }

    public void activate(Char8 c) {
        //During the influence calculation this turn, you count as having 2 more influence
        for (Island island : game.getDashboard().getIslands()) {
            island.setExtraInfluence(game.getCurrentPlayer(), 2);
        }
    }

    public void activate(Char9 c) {
        //Choose a color of Student: during the influence calculation this turn, that color adds no influence
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar9(game, controller, previousState, c));
        controller.getState().execute();
    }

    public void activate(Char10 c) {
        //You may exchange up to 2 Students between your Entrance and your Dining Room
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar10(game, controller, previousState, c));
    }

    public void activate(Char11 c) {
        //Take 1 student from this card and place it in your Dining Room
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar11(game, controller, previousState, c));
        controller.getState().execute();
    }

    public void activate(Char12 c) {
        //Choose a type of student: every player (including the current player) must return
        // 3 students of that type from their dining room to the bag
        ResumableState previousState = (ResumableState) controller.getState();
        controller.setState(new EffectChar12(game, controller, previousState, c));
        controller.getState().execute();
    }


    public boolean verifyCoins(CharacterCard c) {
        if ((game.getCurrentPlayer().getSchoolBoard().getCoins()) >= (c.getCost())) {
            game.getCurrentPlayer().getSchoolBoard().spendCoins(c.getCost());
            return true;
        } else {
            return false;
        }
    }
}
