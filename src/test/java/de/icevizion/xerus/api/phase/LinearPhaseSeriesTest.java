package de.icevizion.xerus.api.phase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Patrick Zdarsky / Rxcki
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LinearPhaseSeriesTest {

    private LinearPhaseSeries<SimplePhase> phaseSeries;
    private SimplePhase phase1;
    private SimplePhase phase2;

    @BeforeAll
    void setUp() {
        phaseSeries = new LinearPhaseSeries<>();
        phase1 = new SimplePhase("Phase #1");
        phase2 = new SimplePhase("Phase #2");

        phaseSeries.add(phase1);
        phaseSeries.add(phase2);
    }

    @Order(1)
    @Test
    void testCurrentPhaseIsNull() {
        assertNull(this.phaseSeries.getCurrentPhase());
    }

    @Order(2)
    @Test
    void testSeriesBegin() {
        phaseSeries.start();
        assertEquals(phase1, phaseSeries.getCurrentPhase());
    }

    @Order(3)
    @Test
    void testPhaseAdvancement() {
        phase1.finish();
        phaseSeries.advance();
        assertTrue(phase1.isFinished());
    }


   /* @Test
    void testPhaseAdvancement() {
        phaseSeries.start();
        phase1.finish();
        assertEquals(phase1, phaseSeries.getCurrentPhase());
        assertEquals(0, phaseSeries.size());
        assertTrue(phaseSeries.isLastPhase());

        phase2.finish();

        assertTrue(phaseSeries.isFinished());
    }

    @Test
    void testPause() {
        phaseSeries.start();

        assertFalse(phaseSeries.isPaused());
        phaseSeries.setPaused(true);

        phase1.finish();

        assertEquals(phase1, phaseSeries.getCurrentPhase());
        assertTrue(phase1.isFinished());
        assertFalse(phase1.isRunning());
    }

    @Test
    void testPauseResume() {
        phaseSeries.start();
        phaseSeries.setPaused(true);

        phase1.finish();
        assertEquals(phase1, phaseSeries.getCurrentPhase());

        phaseSeries.setPaused(false);

        assertEquals(phase2, phaseSeries.getCurrentPhase());
    }

    @Test
    void setCurrentPhaseIndex() {
        phaseSeries.setCurrentPhaseIndex(1);

        assertEquals(phase2, phaseSeries.getCurrentPhase());
    }*/
}