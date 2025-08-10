package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultTeamServiceTest {

    TeamService teamService;

    Team defaultTeamImpl;

    @BeforeAll
    void init() {
        this.teamService = new StandardTeamService();
        this.defaultTeamImpl = Team.of(Key.key("xerus", "team_red"), 10);
        this.teamService.add(defaultTeamImpl);
    }

    @AfterEach
    void tearDown() {
        this.defaultTeamImpl.clearPlayers();

        if (this.teamService.getTeams().isEmpty()) {
            this.teamService.add(defaultTeamImpl);
        }
    }

    @Test
    void testGetTeamByPlayer(Env env) {
        final Instance instance = env.createFlatInstance();
        final Player player = env.createPlayer(instance, Pos.ZERO);
        assertFalse(teamService.getTeam(player).isPresent());
        player.remove();
        env.destroyInstance(instance);
    }

    @Test
    void testGetTeams() {
        assertTrue(this.teamService.hasTeams());
        assertSame(1, this.teamService.getTeams().size());
    }

    @Test
    void testRemoveTeam() {
        this.teamService.remove(defaultTeamImpl);
        assertFalse(this.teamService.hasTeams());
    }

    @Test
    void testRemoveWithIdentifier() {
        assertFalse(this.teamService.getTeams().isEmpty());
        this.teamService.remove(Key.key("xerus", "team_red"));
        assertTrue(this.teamService.getTeams().isEmpty());
    }

    @Test
    void testClearMethod() {
        this.teamService.clear();
        assertFalse(this.teamService.hasTeams());
    }
}