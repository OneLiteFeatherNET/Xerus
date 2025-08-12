package net.theevilreaper.xerus.api.phase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
