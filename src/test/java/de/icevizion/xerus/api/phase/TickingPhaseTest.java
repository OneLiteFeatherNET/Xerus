package de.icevizion.xerus.api.phase;

import de.icevizion.xerus.api.mocks.TestTickingPhase;
import net.minestom.server.timer.Scheduler;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class TickingPhaseTest {

    @Test
    void testTickingPhaseWithInterval() {
        var phase = new TestTickingPhase("Test", ChronoUnit.DAYS, 1);

        assertNotNull(phase);
        assertEquals("Test", phase.getName());
        assertEquals(1, phase.getInterval());
        assertNotSame(ChronoUnit.ERAS, phase.getTemporalUnit());
        assertNotNull(phase.getCurrentPhase());
        assertNull(phase.getScheduledTask());
    }

    @Test
    void testTickingPhaseWithoutInterval() {
        var phase = new TestTickingPhase("Test C", ChronoUnit.CENTURIES);
        assertNotNull(phase);
        var task = Scheduler.newScheduler().buildTask(() -> {
            throw new RuntimeException("Runnable called");
        }).schedule();

        phase.setScheduledTask(task);
        assertNotNull(phase.getScheduledTask());
    }
}