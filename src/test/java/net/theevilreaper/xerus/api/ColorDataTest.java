package net.theevilreaper.xerus.api;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.color.Color;
import net.minestom.server.color.DyeColor;
import net.minestom.server.color.TeamColor;
import net.minestom.server.item.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class ColorDataTest {

    @Test
    void testAquaData() {
        var aquaData = ColorData.AQUA;

        assertNotNull(aquaData);
        assertSame(DyeColor.LIGHT_BLUE, aquaData.getDyeColor());
        assertSame(NamedTextColor.AQUA, aquaData.getChatColor());
        assertNotSame(Material.ACACIA_FENCE, aquaData.getMaterial());
        assertSame("colorAqua", aquaData.getTranslateKey());
        assertNotSame(new Color(10, 2, 45), aquaData.getColor());
    }

    @Test
    void testLength() {
        assertSame(16, ColorData.getValues().length);
    }

    @ParameterizedTest
    @EnumSource(ColorData.class)
    void testToTeamColorMatchesChatColor(ColorData colorData) {
        TeamColor teamColor = colorData.toTeamColor();
        NamedTextColor chatColor = colorData.getChatColor();

        assertNotNull(teamColor);
        assertEquals(chatColor.red(), teamColor.red());
        assertEquals(chatColor.green(), teamColor.green());
        assertEquals(chatColor.blue(), teamColor.blue());
    }
}
