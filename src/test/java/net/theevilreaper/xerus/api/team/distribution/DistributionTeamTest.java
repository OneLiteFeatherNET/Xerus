package net.theevilreaper.xerus.api.team.distribution;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DistributionTeamTest {

    private final DistributionTeam blueTeam = new DistributionTeam("blue");

    @Test
    void testTeamWithName() {
        var team = new DistributionTeam("Test");
        assertSame("Test", team.name());
    }

    @Order(1)
    @Test
    void testWithNameAndPlayers() {
        var team = new DistributionTeam("Yellow", List.of(new DistributionPlayer(UUID.randomUUID(), 1)));

        assertSame("Yellow", team.name());
        assertFalse(team.players().isEmpty());
    }

    @Order(2)
    @Test
    void testStaticCreationWithName() {
        var team = new DistributionTeam("Hallo");
        assertNotSame("Test", team.name());
    }

    @Order(3)
    @Test
    void testStaticCreationWithNameAndPlayers() {
        var team = new DistributionTeam("Red", List.of());
        assertSame("Red", team.name());
        assertSame(0, team.length());
    }

    @Order(4)
    @Test
    void testAddPlayer() {
        blueTeam.add(new DistributionPlayer(UUID.randomUUID(), -10));
        assertSame(1, blueTeam.length());
    }

    @Order(5)
    @Test
    void testEloSum() {
        assertSame(-10, blueTeam.sum());
    }
}
