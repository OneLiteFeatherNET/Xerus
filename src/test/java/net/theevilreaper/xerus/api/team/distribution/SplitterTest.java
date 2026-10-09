package net.theevilreaper.xerus.api.team.distribution;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SplitterTest {

    private static DistributionTeam[] createTeams(int amount) {
        DistributionTeam[] teams = new DistributionTeam[amount];
        for (int i = 0; i < amount; i++) {
            teams[i] = new DistributionTeam(Key.key("xerus", "team" + i));
        }
        return teams;
    }

    private static DistributionPlayer[] createPlayers(int amount) {
        DistributionPlayer[] players = new DistributionPlayer[amount];
        for (int i = 0; i < amount; i++) {
            players[i] = new DistributionPlayer(UUID.randomUUID(), 100 + i);
        }
        return players;
    }

    @Test
    void testTooManyPlayersForEvenTeams() {
        DistributionTeam[] teams = createTeams(2);
        DistributionPlayer[] players = createPlayers(5);
        Splitter splitter = new Splitter();

        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> splitter.compute(teams, players, new ArrayList<>(), 2, true, false)
        );
    }

    @Test
    void testTooManyPlayersWithExistingTeamMembers() {
        DistributionTeam[] teams = createTeams(2);
        teams[0].add(new DistributionPlayer(UUID.randomUUID(), 100));
        DistributionPlayer[] players = createPlayers(4);
        Splitter splitter = new Splitter();

        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> splitter.compute(teams, players, new ArrayList<>(), 2, true, false)
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testPlayersFitIntoEvenTeams(boolean lowVariance) {
        DistributionTeam[] teams = createTeams(2);
        DistributionPlayer[] players = createPlayers(4);

        DistributionTeam[] result = new Splitter().compute(teams, players, new ArrayList<>(), 2, true, lowVariance);

        assertNotNull(result);
        for (DistributionTeam team : result) {
            assertEquals(2, team.length());
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testUnevenTeamsIgnoreTeamSize(boolean lowVariance) {
        DistributionTeam[] teams = createTeams(2);
        DistributionPlayer[] players = createPlayers(5);

        DistributionTeam[] result = new Splitter().compute(teams, players, new ArrayList<>(), 2, false, lowVariance);

        assertNotNull(result);
        assertEquals(5, result[0].length() + result[1].length());
    }
}
