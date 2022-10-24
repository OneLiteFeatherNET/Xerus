package de.icevizion.xerus.api.team.event;

import de.icevizion.xerus.api.team.Team;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MultiPlayerTeamEventTest {

    static Team mockedTeam = Mockito.mock(Team.class);
    static Player mockedPlayer = Mockito.mock(Player.class);

    @Test
    void testEventConstructor() {
        var event = new MultiPlayerTeamEvent<>(mockedTeam, Set.of(mockedPlayer), TeamAction.ADD, true);
        assertTrue(event.isCancelled());
    }

    @Test
    void testPlayerMultiTeamEvent() {
        var event = new MultiPlayerTeamEvent<>(mockedTeam, Set.of(mockedPlayer), TeamAction.ADD);

        assertNotSame(3, event.getPlayers().size());
        assertSame(TeamAction.ADD, event.getTeamAction());
        assertSame(mockedTeam, event.getTeam());

        event.setCancelled(true);

        assertTrue(event.isCancelled());
    }
}