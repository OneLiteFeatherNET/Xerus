package net.theevilreaper.xerus.api.phase;

import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimedPhaseTest {

    private static class CountingTimedPhase extends TimedPhase {

        private int finishCalls;

        CountingTimedPhase() {
            super("Timed", ChronoUnit.SECONDS);
        }

        @Override
        protected void onFinish() {
            finishCalls++;
        }

        @Override
        public void onUpdate() {
        }
    }

    @Test
    void testOnFinishCalledOnce() {
        CountingTimedPhase phase = new CountingTimedPhase();

        phase.finish();
        phase.finish();

        assertEquals(1, phase.finishCalls);
    }
}
