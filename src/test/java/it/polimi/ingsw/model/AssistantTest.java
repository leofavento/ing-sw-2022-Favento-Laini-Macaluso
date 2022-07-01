package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test used to verify the proper functioning of the assistants
 */
class AssistantTest {
    @Test
    public void testAssistant() {
        Assistant tiger1 = Assistant.TIGER;
        Assistant tiger2 = Assistant.TIGER;
        Assistant ostrich = Assistant.OSTRICH;

        assertNotSame(tiger1, ostrich);
        assertEquals(tiger1.getMovements(), ostrich.getMovements());
        assertNotEquals(tiger1.getValue(), ostrich.getValue());

        assertSame(tiger2, tiger1);
        assertEquals(tiger1.getMovements(), tiger2.getMovements());
        assertEquals(tiger1.getValue(), tiger2.getValue());
    }
}