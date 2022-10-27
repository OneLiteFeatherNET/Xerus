package de.icevizion.xerus.api.phase;

import de.icevizion.xerus.api.mocks.TestTickingPhase;
import net.minestom.server.timer.Scheduler;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.timer.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
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