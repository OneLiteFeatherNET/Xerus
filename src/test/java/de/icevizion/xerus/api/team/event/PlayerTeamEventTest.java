package de.icevizion.xerus.api.team.event;

import de.icevizion.xerus.api.team.Team;
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
        var mockedTeam = Mockito.mock(Team.class);
        var mockedPlayer = Mockito.mock(Player.class);

        var event = new PlayerTeamEvent<>(mockedPlayer, mockedTeam, TeamAction.ADD);

        assertNotSame(UUID.randomUUID(), event.getPlayer().getUuid());
        assertSame(TeamAction.ADD, event.getTeamAction());
        assertSame(mockedTeam, event.getTeam());

        event.setCancelled(true);

        assertTrue(event.isCancelled());

    }
}