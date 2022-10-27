package de.icevizion.xerus.api.team.distribution;

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
class DistributionTeamTestImpl {

    private final DistributionTeam blueTeam = DistributionTeam.of("blue");

    @Test
    void testTeamWithName() {
        var team = new DistributionTeam("Test");
        assertSame("Test", team.getName());
    }

    @Order(1)
    @Test
    void testWithNameAndPlayers() {
        var team = new DistributionTeam("Yellow", List.of(DistributionPlayer.of(UUID.randomUUID(), 1)));

        assertSame("Yellow", team.getName());
        assertFalse(team.getPlayers().isEmpty());
    }

    @Order(2)
    @Test
    void testStaticCreationWithName() {
        var team = DistributionTeam.of("Hallo");
        assertNotSame("Test", team.getName());
    }

    @Order(3)
    @Test
    void testStaticCreationWithNameAndPlayers() {
        var team = DistributionTeam.of("Red", List.of());
        assertSame("Red", team.getName());
        assertSame(0, team.length());
    }

    @Order(4)
    @Test
    void testAddPlayer() {
        blueTeam.add(DistributionPlayer.of(UUID.randomUUID(), -10));
        assertSame(1, blueTeam.length());
    }

    @Order(5)
    @Test
    void testEloSum() {
        assertSame(-10, blueTeam.sum());
    }
}
