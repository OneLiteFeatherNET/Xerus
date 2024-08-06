package de.icevizion.xerus.api.phase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PhaseStructureTest {

    Phase phase;

    @BeforeAll
    void init() {
        this.phase = new Phase() {
            @Override
            protected void onStart() {
                throw new RuntimeException("Start called");
            }
        };
        this.phase.setFinishedCallback(() -> {
            throw new RuntimeException("Finish callback");
        });
    }

    @Order(1)
    @Test
    void testConstructorWithName() {
        var phase = new Phase("Test Phase") {
            @Override
            protected void onStart() {

            }
        };
        assertNotNull(phase);
    }

    @Order(2)
    @Test
    void testStartMethod() {
        assertThrowsExactly(RuntimeException.class, phase::start, "Start called");
    }

    @Order(3)
    @Test
    void testFinishMethod() {
        assertThrowsExactly(RuntimeException.class, phase::finish, "Finish callback");
    }

    @Order(4)
    @Test
    void testGetPhase() {
        assertSame(this.phase, this.phase.getCurrentPhase());
    }

    @Order(5)
    @Test
    void testSetName() {
        this.phase.setName("New name");
        assertSame("New name", this.phase.getName());
    }

    @Order(6)
    @Test
    void testSetNameWithException() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> this.phase.setName(" "), "The name can't be empty");
    }

    @Order(7)
    @Test
    void testIsRunning() {
        assertFalse(this.phase.isRunning());
    }

    @Order(8)
    @Test
    void testIsFinished() {
        assertTrue(this.phase.isFinished());
    }

    @Order(9)
    @Test
    void testSetFinish() {
        this.phase.setFinished(false);
        assertFalse(this.phase.isFinished());
    }

    @Order(10)
    @Test
    void testIsSkipping() {
        assertFalse(this.phase.isSkipping());
    }

    @Order(11)
    @Test
    void testSetSkipping() {
        this.phase.setSkipping(true);
        assertTrue(this.phase.isSkipping());
    }

    @Order(12)
    @Test
    void testSetFinishCallback() {
        this.phase.setFinishedCallback(null);
        this.phase.finish();
        assertNotNull(this.phase);
    }
}