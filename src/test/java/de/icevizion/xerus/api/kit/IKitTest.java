package de.icevizion.xerus.api.kit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;

import static de.icevizion.xerus.api.kit.IKit.MAX_ARMOR_ITEMS;
import static de.icevizion.xerus.api.kit.IKit.MAX_HOT_BAR_ITEMS;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IKitTest {

    IKit kit;

    @BeforeAll
    void init() {
        this.kit = Kit.of("TestKit", 9, true);
    }

    @Test
    void testMaxArmorItems() {
        assertSame(4, MAX_ARMOR_ITEMS);
    }

    @Test
    void testMaxHotArmorItems() {
        assertSame(9, MAX_HOT_BAR_ITEMS);
    }

    @Test
    void testGetIdentifier() {
        assertEquals("TestKit", this.kit.getIdentifier());
    }

    @Test
    void testGetName() {
        assertEquals("TestKit", this.kit.getName());
    }

    @Test
    void testGetNameWithLocale() {
        assertEquals("TestKit", this.kit.getName(Locale.ENGLISH));
    }

    @Test
    void testGetDescription() {
        assertNull(this.kit.getDescription());
    }

    @Test
    void testGetDescriptionWithLocale() {
        assertNull(this.kit.getDescription(Locale.ENGLISH));
    }

    @Test
    void testGetIcon() {
        assertNull(this.kit.getIcon());
    }
}