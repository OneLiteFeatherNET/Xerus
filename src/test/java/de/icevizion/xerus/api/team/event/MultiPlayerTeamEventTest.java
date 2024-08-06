package de.icevizion.xerus.api.team.event;

import de.icevizion.xerus.api.team.TeamImpl;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MultiPlayerTeamEventTest {

    static TeamImpl mockedTeamImpl = Mockito.mock(TeamImpl.class);
    static Player mockedPlayer = Mockito.mock(Player.class);

    @Test
    void testEventConstructor() {
        var event = MultiPlayerTeamEvent.addEvent(mockedTeamImpl, Set.of(mockedPlayer));
        event.setCancelled(true);
        assertTrue(event.isCancelled());
    }

    @Test
    void testPlayerMultiTeamEvent() {
        var event = MultiPlayerTeamEvent.addEvent(mockedTeamImpl, Set.of(mockedPlayer));

        assertNotSame(3, event.getPlayers().size());
        assertSame(TeamEvent.Action.ADD, event.getAction());
        assertSame(mockedTeamImpl, event.getTeam());

        event.setCancelled(true);

        assertTrue(event.isCancelled());
    }
}