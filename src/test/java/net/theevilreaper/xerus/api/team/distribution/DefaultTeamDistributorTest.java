package net.theevilreaper.xerus.api.team.distribution;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultTeamDistributorTest {

    @Test
    void testSplitterLowVarianceFullCapacity() {
        Splitter splitter = new Splitter();
        DistributionTeam[] dTeams = new DistributionTeam[]{
                new DistributionTeam(Key.key("xerus", "red")),
                new DistributionTeam(Key.key("xerus", "blue"))
        };
        DistributionPlayer[] dPlayers = new DistributionPlayer[]{
                new DistributionPlayer(UUID.randomUUID(), 100),
                new DistributionPlayer(UUID.randomUUID(), 200),
                new DistributionPlayer(UUID.randomUUID(), 150),
                new DistributionPlayer(UUID.randomUUID(), 180)
        };

        // teamSize = 1, so both teams reach full capacity quickly
        assertDoesNotThrow(() -> {
            DistributionTeam[] result = splitter.compute(dTeams, dPlayers, new ArrayList<>(), 1, true, true);
            assertNotNull(result);
        });
    }

    @Test
    void testSplitterBruteForce() {
        Splitter splitter = new Splitter();
        DistributionTeam[] dTeams = new DistributionTeam[]{
                new DistributionTeam(Key.key("xerus", "red")),
                new DistributionTeam(Key.key("xerus", "blue"))
        };
        DistributionPlayer[] dPlayers = new DistributionPlayer[]{
                new DistributionPlayer(UUID.randomUUID(), 100),
                new DistributionPlayer(UUID.randomUUID(), 200)
        };

        assertDoesNotThrow(() -> {
            DistributionTeam[] result = splitter.compute(dTeams, dPlayers, new ArrayList<>(), 2, true, false);
            assertNotNull(result);
        });
    }
}
