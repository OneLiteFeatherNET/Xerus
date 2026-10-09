package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeamTest {

    @Test
    void testInvalidCapacityUpdate() {
        Team team = Team.of(Key.key("xerus", "test"));
        assertNotNull(team);
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> team.setCapacity(-5),
                "The capacity of the team can't be negative"
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

        team.setCapacity(-1);
        assertEquals(-1, team.getCapacity());
    }

    @Test
    void testTeamEquality() {
        Team team1 = Team.of(Key.key("xerus", "test"));
        Team team2 = Team.of(Key.key("xerus", "test"));

        assertEquals(0, team1.compare(team1, team2));
        assertEquals(team1, team2);
        assertEquals(team1.hashCode(), team2.hashCode());
    }

    @Test
    void testCanJoinWhenOverCapacity() {
        Team team = Team.of(Key.key("xerus", "test"), 5);
        for (int i = 0; i < 3; i++) {
            team.getPlayers().add(Mockito.mock(Player.class));
        }
        assertTrue(team.canJoin());

        team.setCapacity(3);
        assertFalse(team.canJoin());

        team.setCapacity(2);
        assertFalse(team.canJoin());
    }
}
