package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeamTest {

    Team team;

    @BeforeAll
    void init() {
        this.team = Team.builder()
                .name("Team A")
                .colorData(ColorData.AQUA)
                .build();
    }

    @Test
    void testSetCapacity() {
        assertSame(-1, this.team.getCapacity());
        this.team.setCapacity(12);
        assertSame(12, this.team.getCapacity());
    }

    @Test
    void testCanJoin() {
        var currentTeam = Team.builder().name("Team C").capacity(10).colorData(ColorData.AQUA).build();
        assertTrue(currentTeam.canJoin());
    }

    @Test
    void testHasPlayer() {
        var player = mock(Player.class);
        assertFalse(team.hasPlayer(player));
    }

    @Test
    void testNameOrIdentifierMethods() {
        assertEquals("Team A", this.team.getIdentifier());
        assertNotEquals("Team C", PlainTextComponentSerializer.plainText().serialize(this.team.getName(Locale.ENGLISH)));
        assertEquals("Team A", PlainTextComponentSerializer.plainText().serialize(this.team.getName()));
    }

    @Test
    void testGetPlayers() {
        assertNotNull(team.getPlayers());
        assertTrue(team.getPlayers().isEmpty());
    }

    @Test
    void testHashCode() {
        assertNotEquals(32, this.team.hashCode());
    }
}