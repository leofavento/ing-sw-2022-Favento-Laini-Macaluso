package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {
    @Test
    public void testAssistant() {
        Assistant tiger1 = Assistant.TIGER;
        Assistant tiger2 = Assistant.TIGER;
        Assistant ostrich = Assistant.OSTRICH;

        assertFalse(tiger1.equals(ostrich));
        assertEquals(tiger1.getMovements(), ostrich.getMovements());
        assertNotEquals(tiger1.getValue(), ostrich.getValue());

        assertTrue(tiger2.equals(tiger1));
        assertEquals(tiger1.getMovements(), tiger2.getMovements());
        assertEquals(tiger1.getValue(), tiger2.getValue());
    }
}