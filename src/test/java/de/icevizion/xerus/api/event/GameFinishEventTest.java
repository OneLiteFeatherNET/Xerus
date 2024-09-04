package de.icevizion.xerus.api.event;

import net.minestom.server.event.EventDispatcher;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class GameFinishEventTest {

    @Test
    void testEventConstruction() {
        var gameFinishEvent = new GameFinishEvent<>(FinishReason.UNKNOWN);
        assertNotNull(gameFinishEvent);
        assertEquals(FinishReason.class, gameFinishEvent.reason().getClass());
    }

    @Test
    void testEventFire(@NotNull Env env) {
        var listener = env.listen(GameFinishEvent.class);

        listener.followup(gameFinishEvent -> {
            assertNotEquals(FinishReason.TEAM_WIN, gameFinishEvent.reason());
            assertEquals(FinishReason.ALL_DEAD, gameFinishEvent.reason());
        });

        EventDispatcher.call(new GameFinishEvent<>(FinishReason.ALL_DEAD));
    }

    private enum FinishReason {
        ALL_DEAD, UNKNOWN, TEAM_WIN
    }

}