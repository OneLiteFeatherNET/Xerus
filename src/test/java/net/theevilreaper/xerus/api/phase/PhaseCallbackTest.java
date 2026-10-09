package net.theevilreaper.xerus.api.phase;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class PhaseCallbackTest {

    @Test
    void testLinearSeriesKeepsUserCallback() {
        SimplePhase phase1 = new SimplePhase("Phase #1");
        SimplePhase phase2 = new SimplePhase("Phase #2");
        LinearPhaseSeries<SimplePhase> series = new LinearPhaseSeries<>("Series", new ArrayList<>(List.of(phase1, phase2)));

        AtomicBoolean called = new AtomicBoolean(false);
        phase1.addFinishedCallback(() -> called.set(true));

        series.start();
        phase1.finish();

        assertTrue(called.get());
        assertEquals(phase2, series.getCurrentPhase());
    }

    @Test
    void testUserCallbackRunsBeforeAdvance() {
        SimplePhase phase1 = new SimplePhase("Phase #1");
        SimplePhase phase2 = new SimplePhase("Phase #2");
        LinearPhaseSeries<SimplePhase> series = new LinearPhaseSeries<>("Series", new ArrayList<>(List.of(phase1, phase2)));

        List<String> order = new ArrayList<>();
        phase1.addFinishedCallback(() -> order.add("phase1"));
        phase2.addFinishedCallback(() -> order.add("phase2"));
        series.addFinishedCallback(() -> order.add("series"));

        series.start();
        phase1.finish();
        assertFalse(phase2.isFinished());
        phase2.finish();

        assertEquals(List.of("phase1", "phase2", "series"), order);
        assertTrue(series.isFinished());
    }

    @Test
    void testCyclicSeriesKeepsUserCallback() {
        SimplePhase phase1 = new SimplePhase("Phase #1");
        SimplePhase phase2 = new SimplePhase("Phase #2");
        CyclicPhaseSeries<SimplePhase> series = new CyclicPhaseSeries<>("Series", new ArrayList<>(List.of(phase1, phase2)));
        series.setMaxIterations(2);

        List<String> calls = new ArrayList<>();
        phase1.addFinishedCallback(() -> calls.add("phase1"));

        series.start();
        phase1.finish();

        assertEquals(List.of("phase1"), calls);
        assertEquals(phase2, series.getCurrentPhase());
    }

    @Test
    void testMultipleCallbacksRunInOrder() {
        SimplePhase phase = new SimplePhase("Phase");
        List<String> order = new ArrayList<>();
        phase.addFinishedCallback(() -> order.add("first"));
        phase.addFinishedCallback(() -> order.add("second"));

        phase.finish();

        assertEquals(List.of("first", "second"), order);
    }

    @Test
    void testRemoveFinishedCallback() {
        SimplePhase phase = new SimplePhase("Phase");
        AtomicBoolean called = new AtomicBoolean(false);
        Runnable callback = () -> called.set(true);
        phase.addFinishedCallback(callback);

        assertTrue(phase.removeFinishedCallback(callback));
        assertFalse(phase.removeFinishedCallback(callback));

        phase.finish();
        assertFalse(called.get());
    }

    @Test
    void testCallbackCanRemoveItselfDuringFinish() {
        SimplePhase phase = new SimplePhase("Phase");
        AtomicBoolean secondCalled = new AtomicBoolean(false);
        phase.addFinishedCallback(new Runnable() {
            @Override
            public void run() {
                phase.removeFinishedCallback(this);
            }
        });
        phase.addFinishedCallback(() -> secondCalled.set(true));

        assertDoesNotThrow(phase::finish);
        assertTrue(secondCalled.get());
    }

    @Test
    void testFailingCallbackDoesNotBlockOthersOrSeries() {
        SimplePhase phase1 = new SimplePhase("Phase #1");
        SimplePhase phase2 = new SimplePhase("Phase #2");
        LinearPhaseSeries<SimplePhase> series = new LinearPhaseSeries<>("Series", new ArrayList<>(List.of(phase1, phase2)));

        AtomicBoolean secondCalled = new AtomicBoolean(false);
        phase1.addFinishedCallback(() -> {
            throw new IllegalStateException("Broken callback");
        });
        phase1.addFinishedCallback(() -> secondCalled.set(true));

        series.start();
        assertDoesNotThrow(phase1::finish);

        assertTrue(secondCalled.get());
        assertEquals(phase2, series.getCurrentPhase());
    }

    @Test
    @SuppressWarnings("removal")
    void testDeprecatedSetterReplacesCallbacks() {
        SimplePhase phase = new SimplePhase("Phase");
        List<String> calls = new ArrayList<>();
        phase.addFinishedCallback(() -> calls.add("old"));
        phase.setFinishedCallback(() -> calls.add("new"));

        phase.finish();

        assertEquals(List.of("new"), calls);
    }
}
