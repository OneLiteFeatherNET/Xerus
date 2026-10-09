package net.theevilreaper.xerus.api.phase;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testRemovedListenerIsNotCalled() {
        DummyGamePhase phase = new DummyGamePhase();
        EventNode<Event> node = EventNode.all("test");
        phase.initNode(node);
        AtomicBoolean called = new AtomicBoolean(false);

        EventListener<TestEvent> listener = phase.addListener(TestEvent.class, event -> called.set(true));
        assertSame(listener, phase.removeListener(TestEvent.class));

        node.call(new TestEvent());
        assertFalse(called.get());
    }

    @Test
    void testRemoveUnknownListener() {
        DummyGamePhase phase = new DummyGamePhase();
        phase.addListener(TestEvent.class, event -> {});

        assertNull(phase.removeListener(Event.class));
    }
}
