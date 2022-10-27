package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TeamTestImpl {

    Team team;

    @BeforeAll
    void init() {
        this.team = Team.builder().name("Team A").colorData(ColorData.AQUA).build();
    }

    @Test
    void testSetCapacity() {
        assertSame(-1, this.team.getCapacity());
        this.team.setCapacity(12);
        assertSame(12, this.team.getCapacity());
    }

    @Test
    void testCanJoin() {
        var team = Team.builder().name("Team C").colorData(ColorData.AQUA).build();
        assertTrue(team.canJoin());
    }

    @Test
    void testHasPlayer() {
        var player = Mockito.mock(Player.class);
        assertFalse(team.hasPlayer(player));
    }

    @Test
    void testSendMessage() {
        team.sendMessage("Test");
        assertNotNull(team);
    }

    @Test
    void testNameOrIdentifierMethods() {
        assertEquals("Team A", this.team.getIdentifier());
        assertNotEquals("Team C", this.team.getName(Locale.ENGLISH));
        assertEquals("Team A", this.team.getName());
    }

    @Test
    void testGetIcon() {
        assertNull(this.team.getIcon());
    }

    @Test
    void testGetPlayers() {
        assertNotNull(team.getPlayers());
        assertTrue(team.getPlayers().isEmpty());
    }
}