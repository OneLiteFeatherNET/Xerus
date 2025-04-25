package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    static Team team;

    @BeforeAll
    static void init() {
        team = Team.builder()
                .name("Team A")
                .colorData(ColorData.AQUA)
                .build();
    }

    @Test
    void testSetCapacity() {
        assertSame(-1, team.getCapacity());
        team.setCapacity(12);
        assertSame(12, team.getCapacity());
    }

    @Test
    void testCanJoin() {
        var currentTeam = Team.builder().name("Team C").capacity(10).colorData(ColorData.AQUA).build();
        assertTrue(currentTeam.canJoin());
    }

    @Test
    void testNameOrIdentifierMethods() {
        assertEquals("Team A", team.getIdentifier());
        assertNotEquals("Team C", PlainTextComponentSerializer.plainText().serialize(team.getName(Locale.ENGLISH)));
        assertEquals("Team A", PlainTextComponentSerializer.plainText().serialize(team.getName()));
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