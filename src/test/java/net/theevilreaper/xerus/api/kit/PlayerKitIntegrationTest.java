package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.event.EventFilter;

import net.minestom.server.instance.Instance;
import net.minestom.testing.Collector;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import net.theevilreaper.xerus.api.kit.event.PlayerKitChangeEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MicrotusExtension.class)
class PlayerKitIntegrationTest {

    @Test
    void testPlayerKitChangeEventFiring(Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        Kit oldKit = new TestKit(Key.key("xerus", "old_kit"));
        Kit newKit = new TestKit(Key.key("xerus", "new_kit"));

        Collector<PlayerKitChangeEvent> collector = env.trackEvent(PlayerKitChangeEvent.class, EventFilter.PLAYER, player);

        PlayerKitChangeEvent event = new PlayerKitChangeEvent(player, oldKit, newKit);
        EventDispatcher.call(event);

        collector.assertSingle(e -> {
            assertEquals(player, e.getPlayer());
            assertEquals(oldKit, e.getCurrentKit());
            assertEquals(newKit, e.getNewKit());
            assertFalse(e.isCancelled());
        });

        env.destroyInstance(instance, true);
    }
}
