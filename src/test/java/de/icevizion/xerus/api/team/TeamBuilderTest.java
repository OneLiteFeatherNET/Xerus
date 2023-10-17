package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamBuilderTest {

    private static final int TEST_VALUE = 25;

    @Test
    void testCreationWithATeamCreator() {
        var builder = Team.builder(this::createTestTeam)
                .name("Test");

        var team = builder.build();

        assertEquals("Test", team.getName());
        assertTrue(team instanceof TestTeam);
        assertEquals(TEST_VALUE, ((TestTeam) team).getValue());
    }

    private @NotNull Team createTestTeam(@NotNull String name, @NotNull ColorData colorData, int capacity) {
        var team = new TestTeam(name, colorData, capacity);
        team.setValue(TEST_VALUE);
        return team;
    }

    private static final class TestTeam extends TeamImpl {

        private int value;

        /**
         * Creates a new instance from the team.
         *
         * @param name            for the team
         * @param colorData       for the team
         * @param initialCapacity for the team
         */
        protected TestTeam(@NotNull String name, @NotNull ColorData colorData, int initialCapacity) {
            super(name, colorData, initialCapacity);

        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
