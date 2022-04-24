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
}