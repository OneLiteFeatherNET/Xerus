package de.icevizion.xerus.api.kit.event;

import de.icevizion.xerus.api.kit.Kit;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerKitChangeEventTest {

    @Test
    void testPlayerKitChangeEvent() {
        var player = Mockito.mock(Player.class);
        Kit newKit = Kit.of("NewKit", false);
        var event = new PlayerKitChangeEvent(player, null, newKit);
        assertNotSame(UUID.randomUUID(), event.getPlayer().getUuid());
        assertNull(event.getCurrentKit());
        assertNotNull(event.getNewKit());

        event.setCancelled(true);

        assertTrue(event.isCancelled());
    }
}