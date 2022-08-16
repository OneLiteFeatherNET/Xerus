package de.icivizion.xerus.api.phase;

import de.icevizion.xerus.api.phase.CyclicPhaseSeries;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Patrick Zdarsky / Rxcki
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CyclicPhaseSeriesTest {

    private CyclicPhaseSeries<SimplePhase> phaseSeries;
    private SimplePhase phase1, phase2;

    @BeforeAll
    void init() {
        phaseSeries = new CyclicPhaseSeries<>();
        phase1 = new SimplePhase("Phase #1");
        phase2 = new SimplePhase("Phase #2");

        phaseSeries.add(phase1);
        phaseSeries.add(phase2);
    }

    /*@Test
    void testSetMaxIterations() {
        phaseSeries.setMaxIterations(5);

        assertEquals(5, phaseSeries.getMaxIterations());
    }

    @Test
    void testSingleLoop() {
        var runnable = Mockito.mock(Runnable.class);
        phaseSeries.setFinishedCallback(runnable);
        phaseSeries.setMaxIterations(1);
        assertEquals(2, phaseSeries.getIterations());

        phaseSeries.start();

        assertEquals(phase1, phaseSeries.getCurrentPhase());
        phase1.finish();
        phase2.finish();

        assertEquals(1, phaseSeries.getIterations());
        assertTrue(phaseSeries.isFinished());
        Mockito.verify(runnable).run();
    }

    @Test
    void testDoubleLoop() {
        var runnable = Mockito.mock(Runnable.class);
        phaseSeries.setFinishedCallback(runnable);
        phaseSeries.setMaxIterations(2);

        phaseSeries.start();
        phase1.finish();
        phase2.finish();

        assertFalse(phaseSeries.isFinished());
        assertEquals(phase1, phaseSeries.getCurrentPhase());
        assertEquals(1, phaseSeries.getIterations());

        phase1.finish();
        phase2.finish();
        assertEquals(2, phaseSeries.getIterations());
        assertTrue(phaseSeries.isFinished());
        Mockito.verify(runnable).run();
    }*/
}