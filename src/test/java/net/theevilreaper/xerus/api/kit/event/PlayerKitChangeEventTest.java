package net.theevilreaper.xerus.api.kit.event;

import net.theevilreaper.xerus.api.kit.Kit;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class PlayerKitChangeEventTest {

    @Test
    void testPlayerKitChangeEvent(@NotNull Env env) {
        var instance = env.createFlatInstance();
        var player = env.createPlayer(instance, Pos.ZERO);
        Kit newKit = Kit.of(Component.text("NewKit"), false);
        var event = new PlayerKitChangeEvent(player, null, newKit);
        assertNotSame(UUID.randomUUID(), event.getPlayer().getUuid());
        assertNull(event.getCurrentKit());
        assertNotNull(event.getNewKit());

        event.setCancelled(true);

        assertTrue(event.isCancelled());
    }
}