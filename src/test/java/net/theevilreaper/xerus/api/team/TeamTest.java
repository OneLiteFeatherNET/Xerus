package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.theevilreaper.xerus.api.ColorData;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    static Team team;

    @BeforeAll
    static void init() {
        team = Team.of(Key.key("xerus", "team_a"));
    }

    @Test
    void testSetCapacity() {
        assertSame(-1, team.getCapacity());
        team.setCapacity(12);
        assertSame(12, team.getCapacity());
    }

    @Test
    void testCanJoin() {
        var currentTeam = Team.of(Key.key("xerus", "team_c"), 5);
        assertTrue(currentTeam.canJoin());
    }

    @Test
    void testGetPlayers() {
        assertNotNull(team.getPlayers());
        assertTrue(team.getPlayers().isEmpty());
    }

    @Test
    void testHashCode() {
        assertNotEquals(32, team.hashCode());
    }
}