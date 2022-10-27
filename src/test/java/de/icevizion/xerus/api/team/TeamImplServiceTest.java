package de.icevizion.xerus.api.team;

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

    TeamService<TeamImpl> teamService;

    TeamImpl defaultTeamImpl;

    @BeforeAll
    void init() {
        this.teamService = new TeamServiceImpl<>();
        this.defaultTeamImpl = Mockito.mock(TeamImpl.class);
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

}