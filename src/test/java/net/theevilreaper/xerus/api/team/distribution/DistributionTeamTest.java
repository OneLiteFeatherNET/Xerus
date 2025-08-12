package net.theevilreaper.xerus.api.team.distribution;

import net.kyori.adventure.key.Key;
import net.theevilreaper.xerus.api.team.Team;
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

    private final DistributionTeam blueTeam = new DistributionTeam(Key.key("xerus", "blue"));

    @Test
    void testTeamWithName() {
        DistributionTeam team = new DistributionTeam(Key.key("xerus", "test"));
        assertTrue(team.name().asString().contains("test"));
        assertEquals(Key.key("xerus", "test"), team.name());
    }

    @Order(1)
    @Test
    void testWithNameAndPlayers() {
        DistributionTeam team = new DistributionTeam(Key.key("xerus", "yellow"), List.of(new DistributionPlayer(UUID.randomUUID(), 1)));

        assertTrue(team.name().asString().contains("yellow"));
        assertFalse(team.players().isEmpty());
    }

    @Order(2)
    @Test
    void testStaticCreationWithName() {
        var team = new DistributionTeam(Key.key("xerus", "test"));
        assertNotSame(Key.key("xerus", "test"), team.name());
    }

    @Order(3)
    @Test
    void testStaticCreationWithNameAndPlayers() {
        var team = new DistributionTeam(Key.key("xerus", "red"), List.of());
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
