package de.icevizion.xerus.api.team.distribution;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DistributionPlayerTest {

    @Test
    void testConstructorCreation() {
        var player = new DistributionPlayer(UUID.randomUUID(), 100);

        assertNotNull(player);
        assertNotEquals(UUID.randomUUID(), player.uuid());
        assertSame(100, player.elo());
    }

    @Test
    void testStaticCreation() {
        var player = DistributionPlayer.of(UUID.randomUUID(), 1);
        assertNotEquals(0, player.uuid());
        assertNotSame(UUID.randomUUID(), player.uuid());
    }

}