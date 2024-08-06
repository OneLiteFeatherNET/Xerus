package de.icevizion.xerus.api.phase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TickDirectionTest {

    @Test
    void testDirectionUp() {
        assertSame("UP", TickDirection.UP.name());
    }

    @Test
    void testDirectionDown() {
        assertSame("DOWN", TickDirection.DOWN.name());
    }

    @Test
    void testEnumSize() {
        assertSame(2, TickDirection.values().length);
    }
}
