package net.theevilreaper.xerus.api.team.event;

import net.theevilreaper.xerus.api.ColorData;
import net.theevilreaper.xerus.api.team.Team;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class PlayerTeamEventTest {

    @Test
    void testPlayerTeamEvent(Env env) {
        final Instance instance = env.createFlatInstance();
        final Player player = env.createPlayer(instance, Pos.ZERO);
        final Team team = Team.of("Test", ColorData.DARK_AQUA);
        team.addPlayer(player);
        var event = PlayerTeamEvent.addEvent(player, team);

        assertNotSame(UUID.randomUUID(), event.getPlayer().getUuid());
        assertSame(TeamEvent.Action.ADD, event.getAction());
        assertSame(team, event.getTeam());

        event.setCancelled(true);
        assertTrue(event.isCancelled());
    }
}
