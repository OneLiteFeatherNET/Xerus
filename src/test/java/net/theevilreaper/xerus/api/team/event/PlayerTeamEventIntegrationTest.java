package net.theevilreaper.xerus.api.team.event;

import net.kyori.adventure.key.Key;
import net.minestom.server.event.EventFilter;
import net.minestom.testing.Collector;
import net.theevilreaper.xerus.api.ColorData;
import net.theevilreaper.xerus.api.team.Team;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class PlayerTeamEventIntegrationTest {

    @Test
    void testPlayerTeamEvent(@NotNull Env env) {
        final Instance instance = env.createFlatInstance();
        final Player player = env.createPlayer(instance, Pos.ZERO);
        final Team team = Team.of(Key.key("xerus", "team_test"));

        Collector<PlayerTeamEvent> eventCollector = env.trackEvent(PlayerTeamEvent.class, EventFilter.PLAYER, player);

        team.addPlayer(player);

        eventCollector.assertSingle();
        eventCollector.assertSingle(event -> {
            assertNotNull(event.getPlayer(), "Player should not be null");
            assertEquals(player.getUuid(), event.getPlayer().getUuid(), "Player UUID should match");

            assertEquals(team, event.getTeam(), "Team should match the one added");
            assertFalse(event.isCancelled(), "Event should not be cancelled");
        });
    }
}
