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
        dashboard.getMotherNature().setIsland(1);
        assertEquals(1, dashboard.getMotherNature().getIsland());

        dashboard.moveMotherNature(15);
        assertEquals(4, dashboard.getMotherNature().getIsland());

        dashboard.mergeIslands(dashboard.getIslands().get(4), dashboard.getIslands().get(3), dashboard.getIslands().get(5));
        assertEquals(3, dashboard.getMotherNature().getIsland());
        dashboard.moveMotherNature(11);
        assertEquals(4, dashboard.getMotherNature().getIsland());
    }

    @Test
    public void testClouds() {
        Dashboard dashboard = new Dashboard();

        dashboard.placeCloudTiles(2);
        assertArrayEquals(new Student[]{}, dashboard.getClouds().get(0).getStudents().toArray());
        assertArrayEquals(new Student[]{}, dashboard.getClouds().get(1).getStudents().toArray());

        Student s1 = new Student(Color.GREEN);
        Student s2 = new Student(Color.PINK);
        Student s3 = new Student(Color.PINK);
        Student s4 = new Student(Color.RED);
        Student s5 = new Student(Color.BLUE);

        dashboard.getClouds().get(0).addStudent(s1);
        dashboard.getClouds().get(0).addStudent(s2);
        dashboard.getClouds().get(0).addStudent(s3);
        dashboard.getClouds().get(1).addStudent(s4);
        dashboard.getClouds().get(1).addStudent(s5);

        assertArrayEquals(new Student[]{s1, s2, s3}, dashboard.getClouds().get(0).getStudents().toArray());
        assertArrayEquals(new Student[]{s4, s5}, dashboard.getClouds().get(1).getStudents().toArray());
    }
}