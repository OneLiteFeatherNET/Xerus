package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class TeamServiceIntegrationTest {

    private static TeamService teamService;

    @BeforeAll
    static void init() {
        teamService = TeamService.of();
    }

    @AfterEach
    void tearDown() {
        teamService.clear();
        assertFalse(teamService.hasTeams(), "The team service should be empty after each test");
        assertEquals(0, teamService.getTeams().size(), "The team service should not contain any teams");
    }

    @Test
    void testServiceClear() {
        assertFalse(teamService.hasTeams());
        Team team = Team.of(Key.key("xerus", "team_red"));
        teamService.add(team);

        // Clear the service
        teamService.clear();
        assertFalse(teamService.hasTeams());
        assertEquals(0, teamService.getTeams().size());
    }

    @Test
    void testTeamAddition() {
        assertFalse(teamService.hasTeams());
        Team team = Team.of(Key.key("xerus", "team_blue"));
        teamService.add(team);

        assertTrue(teamService.hasTeams());
        assertEquals(1, teamService.getTeams().size());

        Optional<Team> fetchedTeam = teamService.getTeam(Key.key("xerus", "team_blue"));
        assertTrue(fetchedTeam.isPresent());
        assertEquals(team, fetchedTeam.get());
    }

    @Test
    void testTeamRemoval() {
        assertFalse(teamService.hasTeams());
        Team team = Team.of(Key.key("xerus", "team_green"));
        teamService.add(team);

        assertTrue(teamService.hasTeams());
        assertEquals(1, teamService.getTeams().size());

        teamService.remove(team);
        assertFalse(teamService.hasTeams());
    }

    @Test
    void testTeamExists() {
        assertFalse(teamService.hasTeams());
        Team team = Team.of(Key.key("xerus", "team_yellow"));
        teamService.add(team);
        assertTrue(teamService.exists(Key.key("xerus", "team_yellow")));
        assertFalse(teamService.exists(Key.key("xerus", "team_green")));
    }
}
