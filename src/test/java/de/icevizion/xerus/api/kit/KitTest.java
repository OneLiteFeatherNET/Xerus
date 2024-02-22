package de.icevizion.xerus.api.kit;

import de.icevizion.aves.item.IItem;
import de.icevizion.aves.item.Item;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;

import static de.icevizion.xerus.api.kit.Kit.MAX_ARMOR_ITEMS;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KitTest {

    IItem dummyItem;
    Kit kit;

    @BeforeAll
    void init() {
        this.dummyItem = Item.of(ItemStack.AIR);
        this.kit = Kit.of("TestKit", true);
    }

    @Test
    void testMaxArmorItems() {
        assertSame(4, MAX_ARMOR_ITEMS);
    }

    @Test
    void testSetArmorItems() {
        var kit = new KitImpl("Test", null, false);
        assertThrows(NullPointerException.class, () -> kit.setArmorItem(ArmorSlot.BOOTS, dummyItem));
        kit.setItem(0, Item.of(ItemStack.builder(Material.STONE).build()));
        assertNotNull(kit);
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

    @Test
    void testHashCode() {
        assertNotEquals(12, this.kit.hashCode());
    }
}