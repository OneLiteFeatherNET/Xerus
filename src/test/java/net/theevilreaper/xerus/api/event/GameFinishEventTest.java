package net.theevilreaper.xerus.api.event;

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
        assertEquals(FinishReason.class, gameFinishEvent.getReason().getClass());
    }

    @Test
    void testEventFire(@NotNull Env env) {
        var listener = env.listen(GameFinishEvent.class);

        listener.followup(gameFinishEvent -> {
            assertNotEquals(FinishReason.TEAM_WIN, gameFinishEvent.getReason());
            assertEquals(FinishReason.ALL_DEAD, gameFinishEvent.getReason());
        });

        EventDispatcher.call(new GameFinishEvent<>(FinishReason.ALL_DEAD));
    }

    private enum FinishReason {
        ALL_DEAD, UNKNOWN, TEAM_WIN
    }

}