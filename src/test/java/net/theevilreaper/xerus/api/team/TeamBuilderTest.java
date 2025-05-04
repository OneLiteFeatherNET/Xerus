package net.theevilreaper.xerus.api.team;

import net.theevilreaper.xerus.api.ColorData;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamBuilderTest {

    private static final int TEST_VALUE = 25;

    @Test
    void testCreationWithATeamCreator() {
        var builder = Team.builder(this::createTestTeam)
                .colorData(ColorData.BLACK)
                .name("Test");

        var team = builder.build();

        assertEquals("Test", PlainTextComponentSerializer.plainText().serialize(team.getName()));
        assertInstanceOf(TestTeam.class, team);
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
        TestTeam(@NotNull String name, @NotNull ColorData colorData, int initialCapacity) {
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
