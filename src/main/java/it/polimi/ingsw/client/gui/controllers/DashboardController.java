package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardController implements Controller{

    //initializing every element of an Island
    @FXML public ImageView isl1;
    @FXML public Label idIsl1;
    @FXML public ImageView mnIsland1;
    @FXML public ImageView noEntryIsl1;
    @FXML public Label nEIsl1;
    @FXML public ImageView towerIsl1;
    @FXML public Label unitsIsl1;
    @FXML public Label pinkIsl1;
    @FXML public Label greenIsl1;
    @FXML public Label blueIsl1;
    @FXML public Label redIsl1;
    @FXML public Label yellowIsl1;


    @FXML public ImageView isl2;
    @FXML public Label idIsl2;
    @FXML public ImageView mnIsland2;
    @FXML public ImageView noEntryIsl2;
    @FXML public Label nEIsl2;
    @FXML public ImageView towerIsl2;
    @FXML public Label unitsIsl2;
    @FXML public Label pinkIsl2;
    @FXML public Label greenIsl2;
    @FXML public Label blueIsl2;
    @FXML public Label redIsl2;
    @FXML public Label yellowIsl2;


    @FXML public ImageView isl3;
    @FXML public Label idIsl3;
    @FXML public ImageView mnIsland3;
    @FXML public ImageView noEntryIsl3;
    @FXML public Label nEIsl3;
    @FXML public ImageView towerIsl3;
    @FXML public Label unitsIsl3;
    @FXML public Label pinkIsl3;
    @FXML public Label greenIsl3;
    @FXML public Label blueIsl3;
    @FXML public Label redIsl3;
    @FXML public Label yellowIsl3;


    @FXML public ImageView isl4;
    @FXML public Label idIsl4;
    @FXML public ImageView mnIsland4;
    @FXML public ImageView noEntryIsl4;
    @FXML public Label nEIsl4;
    @FXML public ImageView towerIsl4;
    @FXML public Label unitsIsl4;
    @FXML public Label pinkIsl4;
    @FXML public Label greenIsl4;
    @FXML public Label blueIsl4;
    @FXML public Label redIsl4;
    @FXML public Label yellowIsl4;


    @FXML public ImageView isl5;
    @FXML public Label idIsl5;
    @FXML public ImageView mnIsland5;
    @FXML public ImageView noEntryIsl5;
    @FXML public Label nEIsl5;
    @FXML public ImageView towerIsl5;
    @FXML public Label unitsIsl5;
    @FXML public Label pinkIsl5;
    @FXML public Label greenIsl5;
    @FXML public Label blueIsl5;
    @FXML public Label redIsl5;
    @FXML public Label yellowIsl5;


    @FXML public ImageView isl6;
    @FXML public Label idIsl6;
    @FXML public ImageView mnIsland6;
    @FXML public ImageView noEntryIsl6;
    @FXML public Label nEIsl6;
    @FXML public ImageView towerIsl6;
    @FXML public Label unitsIsl6;
    @FXML public Label pinkIsl6;
    @FXML public Label greenIsl6;
    @FXML public Label blueIsl6;
    @FXML public Label redIsl6;
    @FXML public Label yellowIsl6;


    @FXML public ImageView isl7;
    @FXML public Label idIsl7;
    @FXML public ImageView mnIsland7;
    @FXML public ImageView noEntryIsl7;
    @FXML public Label nEIsl7;
    @FXML public ImageView towerIsl7;
    @FXML public Label unitsIsl7;
    @FXML public Label pinkIsl7;
    @FXML public Label greenIsl7;
    @FXML public Label blueIsl7;
    @FXML public Label redIsl7;
    @FXML public Label yellowIsl7;


    @FXML public ImageView isl8;
    @FXML public Label idIsl8;
    @FXML public ImageView mnIsland8;
    @FXML public ImageView noEntryIsl8;
    @FXML public Label nEIsl8;
    @FXML public ImageView towerIsl8;
    @FXML public Label unitsIsl8;
    @FXML public Label pinkIsl8;
    @FXML public Label greenIsl8;
    @FXML public Label blueIsl8;
    @FXML public Label redIsl8;
    @FXML public Label yellowIsl8;


    @FXML public ImageView isl9;
    @FXML public Label idIsl9;
    @FXML public ImageView mnIsland9;
    @FXML public ImageView noEntryIsl9;
    @FXML public Label nEIsl9;
    @FXML public ImageView towerIsl9;
    @FXML public Label unitsIsl9;
    @FXML public Label pinkIsl9;
    @FXML public Label greenIsl9;
    @FXML public Label blueIsl9;
    @FXML public Label redIsl9;
    @FXML public Label yellowIsl9;


    @FXML public ImageView isl10;
    @FXML public Label idIsl10;
    @FXML public ImageView mnIsland10;
    @FXML public ImageView noEntryIsl10;
    @FXML public Label nEIsl10;
    @FXML public ImageView towerIsl10;
    @FXML public Label unitsIsl10;
    @FXML public Label pinkIsl10;
    @FXML public Label greenIsl10;
    @FXML public Label blueIsl10;
    @FXML public Label redIsl10;
    @FXML public Label yellowIsl10;


    @FXML public ImageView isl11;
    @FXML public Label idIsl11;
    @FXML public ImageView mnIsland11;
    @FXML public ImageView noEntryIsl11;
    @FXML public Label nEIsl11;
    @FXML public ImageView towerIsl11;
    @FXML public Label unitsIsl11;
    @FXML public Label pinkIsl11;
    @FXML public Label greenIsl11;
    @FXML public Label blueIsl11;
    @FXML public Label redIsl11;
    @FXML public Label yellowIsl11;


    @FXML public ImageView isl12;
    @FXML public Label idIsl12;
    @FXML public ImageView mnIsland12;
    @FXML public ImageView noEntryIsl12;
    @FXML public Label nEIsl12;
    @FXML public ImageView towerIsl12;
    @FXML public Label unitsIsl12;
    @FXML public Label pinkIsl12;
    @FXML public Label greenIsl12;
    @FXML public Label blueIsl12;
    @FXML public Label redIsl12;
    @FXML public Label yellowIsl12;

    private final static String blackTower = "graphics/towers/blacktower.png";
    private final static String whiteTower = "graphics/towers/whitetower.png";
    private final static String greyTower = "graphics/towers/greytower.png";


    @FXML private TabPane dashboard;
    private HashMap<String, Tab> nicknameToTab;
    private HashMap<String, Controller> nicknameToController;
    private GUI gui;

    public void setup(ArrayList<Player> players) {
        nicknameToTab = new HashMap<>();
        nicknameToController = new HashMap<>();
        for (Player player : players) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/schoolboard.fxml"));
                nicknameToTab.put(player.getNickname(), new Tab(player.getNickname() + "'s Schoolboard"));
                AnchorPane anchorPane = loader.load();
                ScrollPane scroll = new ScrollPane();
                scroll.setContent(anchorPane);
                nicknameToTab.get(player.getNickname()).setContent(scroll);
                Controller controller = loader.getController();
                controller.setGui(gui);
                nicknameToController.put(player.getNickname(), controller);
                ((SchoolboardController) controller).resetAssistantsButtons();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dashboard.getTabs().addAll(nicknameToTab.values());
    }

    public void update() {
        for (Player player : gui.getView().getPlayers()) {
            ((SchoolboardController) nicknameToController.get(player.getNickname())).update(player);
        }

        updateIslands(gui.getView().getDashboard());
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void error(String error) {

    }

    @Override
    public void nextPhase() {

    }

    private void updateIsland1(Dashboard d) {
        //set num units
        unitsIsl1.setText("Units: "+ d.getIslands().get(0).getNumUnits());

        //set students number
        int pink =0, green =0, yellow =0, red =0, blue =0;
        for (Color c: d.getIslands().get(0).getStudents()){
            if (c==Color.YELLOW){
                yellow++;
            }
            if (c==Color.RED){
                red++;
            }
            if (c==Color.PINK){
                pink++;
            }
            if (c==Color.BLUE){
                blue++;
            }
            if (c==Color.GREEN){
                green++;
            }
        }
        pinkIsl1.setText(String.valueOf(pink));
        greenIsl1.setText(String.valueOf(green));
        blueIsl1.setText(String.valueOf(blue));
        redIsl1.setText(String.valueOf(red));
        yellowIsl1.setText(String.valueOf(yellow));

        //set no entry tile
        if (d.getIslands().get(0).getNoEntry()!=0){
            noEntryIsl1.setVisible(true);
            nEIsl1.setText(String.valueOf(d.getIslands().get(0).getNoEntry()));
            nEIsl1.setVisible(true);
        }
        else{
            noEntryIsl1.setVisible(false);
            nEIsl1.setVisible(false);
        }

        //set tower
        if (d.getIslands().get(0).hasTower()){
            towerIsl1.setVisible(true);
            switch (d.getIslands().get(0).getTowerColor()){
                case BLACK -> towerIsl1.setImage(new Image(blackTower));
                case WHITE -> towerIsl1.setImage(new Image(whiteTower));
                case GREY ->  towerIsl1.setImage(new Image(greyTower));
            }
        }
        else {
            towerIsl1.setVisible(false);
        }

        //set mother nature
        mnIsland1.setVisible(d.getMotherNaturePosition() == 0);
    }

    private void updateIsland2(Dashboard d) {
        //set num units
        unitsIsl2.setText("Units: "+ d.getIslands().get(1).getNumUnits());

        //set students number
        int pink =0, green =0, yellow =0, red =0, blue =0;
        for (Color c: d.getIslands().get(1).getStudents()){
            if (c==Color.YELLOW){
                yellow++;
            }
            if (c==Color.RED){
                red++;
            }
            if (c==Color.PINK){
                pink++;
            }
            if (c==Color.BLUE){
                blue++;
            }
            if (c==Color.GREEN){
                green++;
            }
        }
        pinkIsl2.setText(String.valueOf(pink));
        greenIsl2.setText(String.valueOf(green));
        blueIsl2.setText(String.valueOf(blue));
        redIsl2.setText(String.valueOf(red));
        yellowIsl2.setText(String.valueOf(yellow));

        //set no entry tile
        if (d.getIslands().get(1).getNoEntry()!=0){
            noEntryIsl2.setVisible(true);
            nEIsl2.setText(String.valueOf(d.getIslands().get(1).getNoEntry()));
            nEIsl2.setVisible(true);
        }
        else{
            noEntryIsl2.setVisible(false);
            nEIsl2.setVisible(false);
        }

        //set tower
        if (d.getIslands().get(1).hasTower()){
            towerIsl2.setVisible(true);
            switch (d.getIslands().get(1).getTowerColor()){
                case BLACK -> towerIsl2.setImage(new Image(blackTower));
                case WHITE -> towerIsl2.setImage(new Image(whiteTower));
                case GREY ->  towerIsl2.setImage(new Image(greyTower));
            }
        }
        else {
            towerIsl2.setVisible(false);
        }

        //set mother nature
        mnIsland2.setVisible(d.getMotherNaturePosition() == 1);

    }

    private void updateIsland3(Dashboard d) {
        //set num units
        unitsIsl3.setText("Units: "+ d.getIslands().get(2).getNumUnits());

        //set students number
        int pink =0, green =0, yellow =0, red =0, blue =0;
        for (Color c: d.getIslands().get(2).getStudents()){
            if (c==Color.YELLOW){
                yellow++;
            }
            if (c==Color.RED){
                red++;
            }
            if (c==Color.PINK){
                pink++;
            }
            if (c==Color.BLUE){
                blue++;
            }
            if (c==Color.GREEN){
                green++;
            }
        }
        pinkIsl3.setText(String.valueOf(pink));
        greenIsl3.setText(String.valueOf(green));
        blueIsl3.setText(String.valueOf(blue));
        redIsl3.setText(String.valueOf(red));
        yellowIsl3.setText(String.valueOf(yellow));

        //set no entry tile
        if (d.getIslands().get(2).getNoEntry()!=0){
            noEntryIsl3.setVisible(true);
            nEIsl3.setText(String.valueOf(d.getIslands().get(2).getNoEntry()));
            nEIsl3.setVisible(true);
        }
        else{
            noEntryIsl3.setVisible(false);
            nEIsl3.setVisible(false);
        }

        //set tower
        if (d.getIslands().get(2).hasTower()){
            towerIsl3.setVisible(true);
            switch (d.getIslands().get(2).getTowerColor()){
                case BLACK -> towerIsl3.setImage(new Image(blackTower));
                case WHITE -> towerIsl3.setImage(new Image(whiteTower));
                case GREY ->  towerIsl3.setImage(new Image(greyTower));
            }
        }
        else {
            towerIsl3.setVisible(false);
        }

        //set mother nature
        mnIsland3.setVisible(d.getMotherNaturePosition() == 2);

    }

    private void updateIsland4(Dashboard d) {
        if (d.getIslands().size()<=3){
            isl4.setVisible(false);
            idIsl4.setVisible(false);
            unitsIsl4.setVisible(false);
            mnIsland4.setVisible(false);
            noEntryIsl4.setVisible(false);
            nEIsl4.setVisible(false);
            towerIsl4.setVisible(false);
            pinkIsl4.setVisible(false);
            greenIsl4.setVisible(false);
            blueIsl4.setVisible(false);
            redIsl4.setVisible(false);
            yellowIsl4.setVisible(false);
        }
        else{
            //set num units
            unitsIsl4.setText("Units: "+ d.getIslands().get(3).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(3).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl4.setText(String.valueOf(pink));
            greenIsl4.setText(String.valueOf(green));
            blueIsl4.setText(String.valueOf(blue));
            redIsl4.setText(String.valueOf(red));
            yellowIsl4.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(3).getNoEntry()!=0){
                noEntryIsl4.setVisible(true);
                nEIsl4.setText(String.valueOf(d.getIslands().get(3).getNoEntry()));
                nEIsl4.setVisible(true);
            }
            else{
                noEntryIsl4.setVisible(false);
                nEIsl4.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(3).hasTower()){
                towerIsl4.setVisible(true);
                switch (d.getIslands().get(3).getTowerColor()){
                    case BLACK -> towerIsl4.setImage(new Image(blackTower));
                    case WHITE -> towerIsl4.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl4.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl4.setVisible(false);
            }

            //set mother nature
            mnIsland4.setVisible(d.getMotherNaturePosition() == 3);
        }

    }

    private void updateIsland5(Dashboard d) {
        if (d.getIslands().size()<=4){
            isl5.setVisible(false);
            idIsl5.setVisible(false);
            unitsIsl5.setVisible(false);
            mnIsland5.setVisible(false);
            noEntryIsl5.setVisible(false);
            nEIsl5.setVisible(false);
            towerIsl5.setVisible(false);
            pinkIsl5.setVisible(false);
            greenIsl5.setVisible(false);
            blueIsl5.setVisible(false);
            redIsl5.setVisible(false);
            yellowIsl5.setVisible(false);
        }
        else{
            //set num units
            unitsIsl5.setText("Units: "+ d.getIslands().get(4).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(4).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl5.setText(String.valueOf(pink));
            greenIsl5.setText(String.valueOf(green));
            blueIsl5.setText(String.valueOf(blue));
            redIsl5.setText(String.valueOf(red));
            yellowIsl5.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(4).getNoEntry()!=0){
                noEntryIsl5.setVisible(true);
                nEIsl5.setText(String.valueOf(d.getIslands().get(4).getNoEntry()));
                nEIsl5.setVisible(true);
            }
            else{
                noEntryIsl5.setVisible(false);
                nEIsl5.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(4).hasTower()){
                towerIsl5.setVisible(true);
                switch (d.getIslands().get(4).getTowerColor()){
                    case BLACK -> towerIsl5.setImage(new Image(blackTower));
                    case WHITE -> towerIsl5.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl5.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl5.setVisible(false);
            }

            //set mother nature
            mnIsland5.setVisible(d.getMotherNaturePosition() == 4);
        }

    }

    private void updateIsland6(Dashboard d) {
        if (d.getIslands().size()<=5){
            isl6.setVisible(false);
            idIsl6.setVisible(false);
            unitsIsl6.setVisible(false);
            mnIsland6.setVisible(false);
            noEntryIsl6.setVisible(false);
            nEIsl6.setVisible(false);
            towerIsl6.setVisible(false);
            pinkIsl6.setVisible(false);
            greenIsl6.setVisible(false);
            blueIsl6.setVisible(false);
            redIsl6.setVisible(false);
            yellowIsl6.setVisible(false);
        }
        else{
            //set num units
            unitsIsl6.setText("Units: "+ d.getIslands().get(5).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(5).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl6.setText(String.valueOf(pink));
            greenIsl6.setText(String.valueOf(green));
            blueIsl6.setText(String.valueOf(blue));
            redIsl6.setText(String.valueOf(red));
            yellowIsl6.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(5).getNoEntry()!=0){
                noEntryIsl6.setVisible(true);
                nEIsl6.setText(String.valueOf(d.getIslands().get(5).getNoEntry()));
                nEIsl6.setVisible(true);
            }
            else{
                noEntryIsl6.setVisible(false);
                nEIsl6.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(5).hasTower()){
                towerIsl6.setVisible(true);
                switch (d.getIslands().get(5).getTowerColor()){
                    case BLACK -> towerIsl6.setImage(new Image(blackTower));
                    case WHITE -> towerIsl6.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl6.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl6.setVisible(false);
            }

            //set mother nature
            mnIsland6.setVisible(d.getMotherNaturePosition() == 5);
        }
    }

    private void updateIsland7(Dashboard d) {
        if (d.getIslands().size()<=6){
            isl7.setVisible(false);
            idIsl7.setVisible(false);
            unitsIsl7.setVisible(false);
            mnIsland7.setVisible(false);
            noEntryIsl7.setVisible(false);
            nEIsl7.setVisible(false);
            towerIsl7.setVisible(false);
            pinkIsl7.setVisible(false);
            greenIsl7.setVisible(false);
            blueIsl7.setVisible(false);
            redIsl7.setVisible(false);
            yellowIsl7.setVisible(false);
        }
        else{
            //set num units
            unitsIsl7.setText("Units: "+ d.getIslands().get(6).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(6).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl7.setText(String.valueOf(pink));
            greenIsl7.setText(String.valueOf(green));
            blueIsl7.setText(String.valueOf(blue));
            redIsl7.setText(String.valueOf(red));
            yellowIsl7.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(6).getNoEntry()!=0){
                noEntryIsl7.setVisible(true);
                nEIsl7.setText(String.valueOf(d.getIslands().get(6).getNoEntry()));
                nEIsl7.setVisible(true);
            }
            else{
                noEntryIsl7.setVisible(false);
                nEIsl7.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(6).hasTower()){
                towerIsl7.setVisible(true);
                switch (d.getIslands().get(6).getTowerColor()){
                    case BLACK -> towerIsl7.setImage(new Image(blackTower));
                    case WHITE -> towerIsl7.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl7.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl7.setVisible(false);
            }

            //set mother nature
            mnIsland7.setVisible(d.getMotherNaturePosition() == 6);
        }

    }

    private void updateIsland8(Dashboard d) {
        if (d.getIslands().size()<=7){
            isl8.setVisible(false);
            idIsl8.setVisible(false);
            unitsIsl8.setVisible(false);
            mnIsland8.setVisible(false);
            noEntryIsl8.setVisible(false);
            nEIsl8.setVisible(false);
            towerIsl8.setVisible(false);
            pinkIsl8.setVisible(false);
            greenIsl8.setVisible(false);
            blueIsl8.setVisible(false);
            redIsl8.setVisible(false);
            yellowIsl8.setVisible(false);
        }
        else{
            //set num units
            unitsIsl8.setText("Units: "+ d.getIslands().get(7).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(7).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl8.setText(String.valueOf(pink));
            greenIsl8.setText(String.valueOf(green));
            blueIsl8.setText(String.valueOf(blue));
            redIsl8.setText(String.valueOf(red));
            yellowIsl8.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(7).getNoEntry()!=0){
                noEntryIsl8.setVisible(true);
                nEIsl8.setText(String.valueOf(d.getIslands().get(7).getNoEntry()));
                nEIsl8.setVisible(true);
            }
            else{
                noEntryIsl8.setVisible(false);
                nEIsl8.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(7).hasTower()){
                towerIsl8.setVisible(true);
                switch (d.getIslands().get(7).getTowerColor()){
                    case BLACK -> towerIsl8.setImage(new Image(blackTower));
                    case WHITE -> towerIsl8.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl8.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl8.setVisible(false);
            }

            //set mother nature
            mnIsland8.setVisible(d.getMotherNaturePosition() == 7);
        }

    }

    private void updateIsland9(Dashboard d) {
        if (d.getIslands().size()<=8){
            isl9.setVisible(false);
            idIsl9.setVisible(false);
            unitsIsl9.setVisible(false);
            mnIsland9.setVisible(false);
            noEntryIsl9.setVisible(false);
            nEIsl9.setVisible(false);
            towerIsl9.setVisible(false);
            pinkIsl9.setVisible(false);
            greenIsl9.setVisible(false);
            blueIsl9.setVisible(false);
            redIsl9.setVisible(false);
            yellowIsl9.setVisible(false);
        }
        else{
            //set num units
            unitsIsl9.setText("Units: "+ d.getIslands().get(8).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(8).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl9.setText(String.valueOf(pink));
            greenIsl9.setText(String.valueOf(green));
            blueIsl9.setText(String.valueOf(blue));
            redIsl9.setText(String.valueOf(red));
            yellowIsl9.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(8).getNoEntry()!=0){
                noEntryIsl9.setVisible(true);
                nEIsl9.setText(String.valueOf(d.getIslands().get(8).getNoEntry()));
                nEIsl9.setVisible(true);
            }
            else{
                noEntryIsl9.setVisible(false);
                nEIsl9.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(8).hasTower()){
                towerIsl9.setVisible(true);
                switch (d.getIslands().get(8).getTowerColor()){
                    case BLACK -> towerIsl9.setImage(new Image(blackTower));
                    case WHITE -> towerIsl9.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl9.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl9.setVisible(false);
            }

            //set mother nature
            mnIsland9.setVisible(d.getMotherNaturePosition() == 8);
        }

    }

    private void updateIsland10(Dashboard d) {
        if (d.getIslands().size()<=9){
            isl10.setVisible(false);
            idIsl10.setVisible(false);
            unitsIsl10.setVisible(false);
            mnIsland10.setVisible(false);
            noEntryIsl10.setVisible(false);
            nEIsl10.setVisible(false);
            towerIsl10.setVisible(false);
            pinkIsl10.setVisible(false);
            greenIsl10.setVisible(false);
            blueIsl10.setVisible(false);
            redIsl10.setVisible(false);
            yellowIsl10.setVisible(false);
        }
        else{
            //set num units
            unitsIsl10.setText("Units: "+ d.getIslands().get(9).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(9).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl10.setText(String.valueOf(pink));
            greenIsl10.setText(String.valueOf(green));
            blueIsl10.setText(String.valueOf(blue));
            redIsl10.setText(String.valueOf(red));
            yellowIsl10.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(9).getNoEntry()!=0){
                noEntryIsl10.setVisible(true);
                nEIsl10.setText(String.valueOf(d.getIslands().get(9).getNoEntry()));
                nEIsl10.setVisible(true);
            }
            else{
                noEntryIsl10.setVisible(false);
                nEIsl10.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(9).hasTower()){
                towerIsl10.setVisible(true);
                switch (d.getIslands().get(9).getTowerColor()){
                    case BLACK -> towerIsl10.setImage(new Image(blackTower));
                    case WHITE -> towerIsl10.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl10.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl10.setVisible(false);
            }

            //set mother nature
            mnIsland10.setVisible(d.getMotherNaturePosition() == 9);
        }

    }

    private void updateIsland11(Dashboard d) {
        if (d.getIslands().size()<=10){
            isl11.setVisible(false);
            idIsl11.setVisible(false);
            unitsIsl11.setVisible(false);
            mnIsland11.setVisible(false);
            noEntryIsl11.setVisible(false);
            nEIsl11.setVisible(false);
            towerIsl11.setVisible(false);
            pinkIsl11.setVisible(false);
            greenIsl11.setVisible(false);
            blueIsl11.setVisible(false);
            redIsl11.setVisible(false);
            yellowIsl11.setVisible(false);
        }
        else{
            //set num units
            unitsIsl11.setText("Units: "+ d.getIslands().get(10).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(10).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl11.setText(String.valueOf(pink));
            greenIsl11.setText(String.valueOf(green));
            blueIsl11.setText(String.valueOf(blue));
            redIsl11.setText(String.valueOf(red));
            yellowIsl11.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(10).getNoEntry()!=0){
                noEntryIsl11.setVisible(true);
                nEIsl11.setText(String.valueOf(d.getIslands().get(10).getNoEntry()));
                nEIsl11.setVisible(true);
            }
            else{
                noEntryIsl11.setVisible(false);
                nEIsl11.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(10).hasTower()){
                towerIsl11.setVisible(true);
                switch (d.getIslands().get(10).getTowerColor()){
                    case BLACK -> towerIsl11.setImage(new Image(blackTower));
                    case WHITE -> towerIsl11.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl11.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl11.setVisible(false);
            }

            //set mother nature
            mnIsland11.setVisible(d.getMotherNaturePosition() == 10);
        }

    }

    private void updateIsland12(Dashboard d) {
        if (d.getIslands().size()<=11){
            isl12.setVisible(false);
            idIsl12.setVisible(false);
            unitsIsl12.setVisible(false);
            mnIsland12.setVisible(false);
            noEntryIsl12.setVisible(false);
            nEIsl12.setVisible(false);
            towerIsl12.setVisible(false);
            pinkIsl12.setVisible(false);
            greenIsl12.setVisible(false);
            blueIsl12.setVisible(false);
            redIsl12.setVisible(false);
            yellowIsl12.setVisible(false);
        }
        else{
            //set num units
            unitsIsl12.setText("Units: "+ d.getIslands().get(11).getNumUnits());

            //set students number
            int pink =0, green =0, yellow =0, red =0, blue =0;
            for (Color c: d.getIslands().get(11).getStudents()){
                if (c==Color.YELLOW){
                    yellow++;
                }
                if (c==Color.RED){
                    red++;
                }
                if (c==Color.PINK){
                    pink++;
                }
                if (c==Color.BLUE){
                    blue++;
                }
                if (c==Color.GREEN){
                    green++;
                }
            }
            pinkIsl12.setText(String.valueOf(pink));
            greenIsl12.setText(String.valueOf(green));
            blueIsl12.setText(String.valueOf(blue));
            redIsl12.setText(String.valueOf(red));
            yellowIsl12.setText(String.valueOf(yellow));

            //set no entry tile
            if (d.getIslands().get(11).getNoEntry()!=0){
                noEntryIsl12.setVisible(true);
                nEIsl12.setText(String.valueOf(d.getIslands().get(11).getNoEntry()));
                nEIsl12.setVisible(true);
            }
            else{
                noEntryIsl12.setVisible(false);
                nEIsl12.setVisible(false);
            }

            //set tower
            if (d.getIslands().get(11).hasTower()){
                towerIsl12.setVisible(true);
                switch (d.getIslands().get(11).getTowerColor()){
                    case BLACK -> towerIsl12.setImage(new Image(blackTower));
                    case WHITE -> towerIsl12.setImage(new Image(whiteTower));
                    case GREY ->  towerIsl12.setImage(new Image(greyTower));
                }
            }
            else {
                towerIsl12.setVisible(false);
            }

            //set mother nature
            mnIsland12.setVisible(d.getMotherNaturePosition() == 11);
        }

    }

    private void updateIslands(Dashboard d){
        updateIsland1(d);
        updateIsland2(d);
        updateIsland3(d);
        updateIsland4(d);
        updateIsland5(d);
        updateIsland6(d);
        updateIsland7(d);
        updateIsland8(d);
        updateIsland9(d);
        updateIsland10(d);
        updateIsland11(d);
        updateIsland12(d);
    }
}
