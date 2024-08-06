package de.icevizion.xerus.api.phase;

import de.icevizion.xerus.api.mocks.TestTickPhase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TickedPhaseTest {

    @Test
    void testTicketPhase() {
        var tickedPhase = new TestTickPhase("Phase");
        assertNotNull(tickedPhase);
        assertNotEquals("Test", tickedPhase.getName());
        assertThrowsExactly(RuntimeException.class, tickedPhase::onStart, "On start");
        assertThrowsExactly(RuntimeException.class, tickedPhase::onUpdate, "On update");
    }
}