package net.theevilreaper.xerus.api.phase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Patrick Zdarsky / Rxcki
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdvancedLinearPhaseSeriesTest {

    @Test
    void onSkipPassthrough() {
        var phaseSeries = new LinearPhaseSeries<SimplePhase>("Test Phase Series");
        var phase1 = Mockito.spy(new SimplePhase());

        phaseSeries.add(phase1);
        phaseSeries.start();
        phaseSeries.onSkip();

        Mockito.verify(phase1).onSkip();
    }

    @Test
    void testPhasesConstructor() {
        var phaseList = new ArrayList<SimplePhase>();
        var phase1 = new SimplePhase(null);
        phaseList.add(phase1);

        var phaseSeries = new LinearPhaseSeries<>(null, phaseList);
        phaseSeries.start();
        assertEquals(phase1, phaseSeries.getCurrentPhase());
    }

    @Test
    void testResumeDoesNotSkipRunningPhase() {
        var phase1 = new SimplePhase("Phase #1");
        var phase2 = new SimplePhase("Phase #2");
        var phaseSeries = new LinearPhaseSeries<>("Test Phase Series", new ArrayList<>(List.of(phase1, phase2)));

        phaseSeries.start();
        phaseSeries.setPaused(true);
        phaseSeries.setPaused(false);

        assertEquals(phase1, phaseSeries.getCurrentPhase());
        assertFalse(phase2.isRunning());
    }

    @Test
    void testResumeAdvancesWhenPhaseFinishedWhilePaused() {
        var phase1 = new SimplePhase("Phase #1");
        var phase2 = new SimplePhase("Phase #2");
        var phaseSeries = new LinearPhaseSeries<>("Test Phase Series", new ArrayList<>(List.of(phase1, phase2)));

        phaseSeries.start();
        phaseSeries.setPaused(true);
        phase1.finish();
        assertEquals(phase1, phaseSeries.getCurrentPhase());

        phaseSeries.setPaused(false);
        assertEquals(phase2, phaseSeries.getCurrentPhase());
        assertTrue(phase2.isRunning());
    }

    @Test
    void testPauseBeforeStart() {
        var phaseSeries = new LinearPhaseSeries<>("Test Phase Series", new ArrayList<>(List.of(new SimplePhase())));

        assertDoesNotThrow(() -> phaseSeries.setPaused(true));
        assertDoesNotThrow(() -> phaseSeries.setPaused(false));
    }
}
