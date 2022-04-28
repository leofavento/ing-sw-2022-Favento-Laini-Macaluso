package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {
    @Test
    public void testPlaceIslands() {
        Dashboard dashboard = new Dashboard();

        assertEquals(0, dashboard.getIslands().size());
        dashboard.placeIslands();
        assertEquals(12, dashboard.getIslands().size());
    }

    @Test
    public void testMotherNature() {
        Dashboard dashboard = new Dashboard();

        dashboard.placeIslands();
        dashboard.setMotherNature(dashboard.getIslands().get(1));
        assertEquals(1, dashboard.getMotherNaturePosition());

        dashboard.moveMotherNature(15);
        assertEquals(4, dashboard.getMotherNaturePosition());

        dashboard.mergeIslands(dashboard.getIslands().get(4), dashboard.getIslands().get(3), dashboard.getIslands().get(5));
        assertEquals(3, dashboard.getMotherNaturePosition());
        dashboard.moveMotherNature(11);
        assertEquals(4, dashboard.getMotherNaturePosition());
    }

    @Test
    public void testClouds() {
        Dashboard dashboard = new Dashboard();

        dashboard.placeCloudTiles(2);
        assertArrayEquals(new Color[]{}, dashboard.getClouds().get(0).getStudents().toArray());
        assertArrayEquals(new Color[]{}, dashboard.getClouds().get(1).getStudents().toArray());

        Color student1 = Color.GREEN;
        Color student2 = Color.PINK;
        Color student3 = Color.PINK;
        Color student4 = Color.RED;
        Color student5 = Color.BLUE;

        dashboard.getClouds().get(0).addStudent(student1);
        dashboard.getClouds().get(0).addStudent(student2);
        dashboard.getClouds().get(0).addStudent(student3);
        dashboard.getClouds().get(1).addStudent(student4);
        dashboard.getClouds().get(1).addStudent(student5);

        assertArrayEquals(new Color[]{student1, student2, student3}, dashboard.getClouds().get(0).getStudents().toArray());
        assertArrayEquals(new Color[]{student4, student5}, dashboard.getClouds().get(1).getStudents().toArray());
    }

    @Test
    public void testCountTowers() {
        Dashboard dashboard = new Dashboard();
        Tower tower = Tower.BLACK;

        dashboard.placeIslands();

        assertEquals(0, dashboard.countTowers(tower));

        dashboard.getIslands().get(3).setTowers(Tower.BLACK);
        dashboard.getIslands().get(6).setTowers(Tower.WHITE);
        dashboard.getIslands().get(1).setTowers(Tower.BLACK);
        dashboard.getIslands().get(8).setTowers(Tower.GREY);
        assertEquals(2, dashboard.countTowers(tower));

        dashboard.getIslands().get(3).setTowers(Tower.GREY);
        assertEquals(1, dashboard.countTowers(tower));
    }

    @Test
    public void testCharactersInitialization() {
        Dashboard dashboard = new Dashboard();

        dashboard.setCharacters();

        assertEquals(3, dashboard.getCharacters().length);
        assertNotEquals(dashboard.getCharacters()[0], dashboard.getCharacters()[1]);
        assertNotEquals(dashboard.getCharacters()[0], dashboard.getCharacters()[2]);
        assertNotEquals(dashboard.getCharacters()[1], dashboard.getCharacters()[2]);
    }
}