package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeamTest {

    @Test
    void testInvalidCapacityUpdate() {
        Team team = Team.of(Key.key("xerus", "test"));
        assertNotNull(team);
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> team.setCapacity(-1),
                "The capacity of the can't be negative"
        );
    }

    @Test
    void testCapacityUpdate() {
        Team team = Team.of(Key.key("xerus", "test"));
        assertNotNull(team);
        assertEquals(-1, team.getCapacity());
        assertTrue(team.canJoin());

        team.setCapacity(10);
        assertEquals(10, team.getCapacity());
    }

    @Test
    void testTeamEquality() {
        Team team1 = Team.of(Key.key("xerus", "test"));
        Team team2 = Team.of(Key.key("xerus", "test"));

        assertEquals(0, team1.compare(team1, team2));
        assertEquals(team1, team2);
        assertEquals(team1.hashCode(), team2.hashCode());
    }
}
