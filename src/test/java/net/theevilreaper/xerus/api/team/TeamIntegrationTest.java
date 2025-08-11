package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.minestom.server.event.EventFilter;
import net.minestom.testing.Collector;
import net.theevilreaper.xerus.api.ColorData;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import net.theevilreaper.xerus.api.team.event.PlayerTeamEvent;
import net.theevilreaper.xerus.api.team.event.TeamAction;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MicrotusExtension.class)
public class TeamIntegrationTest {

    static Team team;

    @BeforeAll
    static void init() {
        team = Team.of(Key.key("xerus", "team_a"), 10);
    }

    @AfterEach
    void tearDown() {
        // Clear the team after each test to ensure a clean state
        team.clearPlayers();
        assertTrue(team.isEmpty());
    }

    @Test
    void testCanJoin(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);
        Team team = Team.of(Key.key("xerus", "team_a"), 1);
        assertTrue(team.canJoin());
        team.addPlayer(player);
        assertFalse(team.canJoin());
        team.removePlayer(player);
        assertTrue(team.canJoin());
        env.destroyInstance(instance, true);
        assertTrue(instance.getPlayers().isEmpty(), "Instance should be empty after destruction");
    }

    @Test
    void testAddPlayer(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);
        team.addPlayer(player);
        assertTrue(team.hasPlayer(player));

        env.destroyInstance(instance, true);
        assertTrue(instance.getPlayers().isEmpty(), "Instance should be empty after destruction");
    }

    @Test
    void testDoublePlayerAdd(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        team.addPlayer(player);
        assertEquals(1, team.getCurrentSize());

        team.addPlayer(player);
        assertEquals(1, team.getCurrentSize());
    }

    @Test
    void testRemovePlayer(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);
        team.addPlayer(player);
        team.removePlayer(player);
        assertFalse(team.hasPlayer(player));

        env.destroyInstance(instance, true);
        assertTrue(instance.getPlayers().isEmpty(), "Instance should be empty after destruction");
    }

    @Test
    void testDoubleRemovePlayer(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        team.addPlayer(player);

        Collector<PlayerTeamEvent> collector = env.trackEvent(PlayerTeamEvent.class, EventFilter.PLAYER, player);

        team.removePlayer(player);
        assertEquals(0, team.getCurrentSize());
        collector.assertSingle(event -> {
            assertEquals(player, event.getPlayer());
            assertEquals(team, event.getTeam());
            assertEquals(TeamAction.REMOVE, event.getAction());
        });

        Collector<PlayerTeamEvent> emptyCollector = env.trackEvent(PlayerTeamEvent.class, EventFilter.PLAYER, player);

        team.removePlayer(player);
        emptyCollector.assertEmpty();
    }

    @Test
    void testHasPlayer(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);
        assertFalse(team.hasPlayer(player));

        team.addPlayer(player);
        assertTrue(team.hasPlayer(player));
        assertEquals(1, team.getCurrentSize());
        Player secondPlayer = env.createPlayer(instance);

        assertFalse(team.hasPlayer(secondPlayer));

        env.destroyInstance(instance, true);
        assertTrue(instance.getPlayers().isEmpty(), "Instance should be empty after destruction");
    }
}
