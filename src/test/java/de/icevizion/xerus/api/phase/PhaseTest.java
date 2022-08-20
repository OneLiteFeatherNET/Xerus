package de.icevizion.xerus.api.phase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Patrick Zdarsky / Rxcki
 */
@ExtendWith(MockitoExtension.class)
class PhaseTest {

    @Test
    void testConstructorNameGetter() {
        var name = "Phase Name";
        var phase = new SimplePhase(name);

        assertEquals(name, phase.getName());
    }

    @Test
    void testStart() {
        var phase = Mockito.spy(new SimplePhase("Test="));

        Mockito.verify(phase, Mockito.never()).onStart();
        phase.start();
        Mockito.verify(phase).onStart();
    }

    @Test
    void testStartDouble() {
        var phase = Mockito.spy(new SimplePhase("Test="));

        Mockito.verify(phase, Mockito.never()).onStart();
        phase.start();
        phase.start();
        Mockito.verify(phase, Mockito.times(1)).onStart();
    }

    @Test
    void startFinishCallback() {
        var phase = new SimplePhase("Test=");
        var callback = Mockito.mock(Runnable.class);
        phase.setFinishedCallback(callback);

        phase.start();
        phase.finish();

        Mockito.verify(callback).run();
    }

    @Test
    void getCurrentPhase() {
        var phase = new SimplePhase("Test=");

        assertEquals(phase, phase.getCurrentPhase());
    }

    @Test
    void isRunning() {
        var phase = new SimplePhase("Test=");

        assertFalse(phase.isRunning());
        phase.start();
        assertTrue(phase.isRunning());
    }

    @Test
    void isFinished() {
        var phase = new SimplePhase("Test=");

        assertFalse(phase.isFinished());
        phase.finish();
        assertTrue(phase.isFinished());
    }

    @Test
    void isFinishedFull() {
        var phase = new SimplePhase("Test=");

        assertFalse(phase.isFinished());
        phase.start();
        assertTrue(phase.isRunning());
        phase.finish();
        assertFalse(phase.isRunning());
        assertTrue(phase.isFinished());
    }

    @Test
    void testDoubleFinished() {
        var phase = new SimplePhase("Test=");
        var callback = Mockito.mock(Runnable.class);
        phase.setFinishedCallback(callback);

        phase.finish();
        phase.finish();

        Mockito.verify(callback, Mockito.times(1)).run();
    }

    @Test
    void setFinished() {
        var phase = new SimplePhase("Test=");

        assertFalse(phase.isFinished());
        phase.setFinished(true);
        assertTrue(phase.isFinished());
    }

    @Test
    void isSkippingAndSetSkipping() {
        var phase = new SimplePhase("Test=");

        phase.setSkipping(true);
        assertTrue(phase.isSkipping());
    }
}