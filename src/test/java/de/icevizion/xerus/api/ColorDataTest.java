package de.icevizion.xerus.api;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.color.Color;
import net.minestom.server.color.DyeColor;
import net.minestom.server.item.Material;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorDataTest {

    @Test
    void testAquaData() {
        var aquaData = ColorData.AQUA;

        assertNotNull(aquaData);
        assertSame(DyeColor.LIGHT_BLUE, aquaData.getDyeColor());
        assertSame(NamedTextColor.AQUA, aquaData.getChatColor());
        assertNotSame(Material.ACACIA_FENCE, aquaData.getStack().material());
        assertSame("colorAqua", aquaData.getTranslateKey());
        assertNotSame(new Color(10, 2, 45), aquaData.getColor());
    }

    @Test
    void testLength() {
        assertSame(16, 16);
    }

}