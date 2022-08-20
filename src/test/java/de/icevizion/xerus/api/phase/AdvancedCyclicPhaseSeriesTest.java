package de.icevizion.xerus.api.phase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Patrick Zdarsky / Rxcki
 */
@ExtendWith(MockitoExtension.class)
class AdvancedCyclicPhaseSeriesTest {

    @Test
    void testSkipIteration() {
        /*var phaseSeries = new CyclicPhaseSeries<SimplePhase>();
        var phase1 = Mockito.spy(new SimplePhase("Phase #1"));
        var phase2 = new SimplePhase("Phase #2");

        phaseSeries.add(phase1);
        phaseSeries.add(phase2);
        phaseSeries.setMaxIterations(2);

        phaseSeries.start();

        assertEquals(phase1, phaseSeries.getCurrentPhase());

        phaseSeries.skipIteration();
        assertEquals(1, phaseSeries.getIterations());
        Mockito.verify(phase1).onSkip();
        assertEquals(phase1, phaseSeries.getCurrentPhase());*/
    }
}
