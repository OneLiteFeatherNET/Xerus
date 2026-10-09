package net.theevilreaper.xerus.api.phase;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GamePhaseTest {

    private static class TestEvent implements Event {
    }

    private static class DummyGamePhase extends GamePhase {
        protected DummyGamePhase() {
            super("Dummy");
        }

        @Override
        protected void onStart() {
        }
    }

    @Test
    void testAddAndRemoveListener() {
        DummyGamePhase phase = new DummyGamePhase();
        AtomicBoolean called = new AtomicBoolean(false);

        EventListener<TestEvent> listener = phase.addListener(TestEvent.class, event -> called.set(true));
        assertNotNull(listener);

        EventListener<? extends Event> removed = phase.removeListener(TestEvent.class);
        assertNotNull(removed);
    }
}
