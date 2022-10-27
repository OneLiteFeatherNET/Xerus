package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeamImplServiceTest {

    TeamService<Team> teamService;

    Team defaultTeamImpl;

    @BeforeAll
    void init() {
        this.teamService = new TeamServiceImpl<>();
        this.defaultTeamImpl = Team.builder().name("Red").capacity(10).colorData(ColorData.DARK_RED).build();
        this.teamService.add(defaultTeamImpl);
    }

    @Test
    void testOtherConstructor() {
        var service = new TeamServiceImpl<>(3);
        assertNotNull(service);
    }

    @Test
    void testGetTeamByPlayer() {
        var testPlayer = Mockito.mock(Player.class);
        assertFalse(teamService.getTeam(testPlayer).isPresent());
    }

    @Test
    void testGetTeams() {
        assertSame(1, this.teamService.getTeams().size());
    }

    @Test
    void testRemoveTeam() {
        this.teamService.remove(defaultTeamImpl);
        assertTrue(this.teamService.getTeams().isEmpty());
        this.teamService.add(defaultTeamImpl);
    }

    @Test
    void testRemoveWithIdentifier() {
        this.teamService.remove("Team A");
        assertFalse(this.teamService.getTeams().isEmpty());
    }

    @Test
    void testClearMethod() {
        this.teamService.clear();
        assertTrue(this.teamService.getTeams().isEmpty());
        this.teamService.add(defaultTeamImpl);
    }
}