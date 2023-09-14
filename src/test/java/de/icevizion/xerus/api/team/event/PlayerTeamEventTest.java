package de.icevizion.xerus.api.team.event;

import de.icevizion.xerus.api.team.TeamImpl;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerTeamEventTest {

    @Test
    void testPlayerTeamEvent() {
        var mockedTeam = Mockito.mock(TeamImpl.class);
        var mockedPlayer = Mockito.mock(Player.class);

        var event = PlayerTeamEvent.addEvent(mockedPlayer, mockedTeam);

        assertNotSame(UUID.randomUUID(), event.getPlayer().getUuid());
        assertSame(TeamEvent.Action.ADD, event.getAction());
        assertSame(mockedTeam, event.getTeam());

        event.setCancelled(true);

        assertTrue(event.isCancelled());

    }
}