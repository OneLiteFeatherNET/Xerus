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
import static de.icevizion.xerus.api.kit.Kit.MAX_HOT_BAR_ITEMS;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KitTest {

    IItem dummyItem;
    Kit kit;

    @BeforeAll
    void init() {
        this.dummyItem = new Item(ItemStack.AIR);
        this.kit = Kit.of("TestKit", 9, true);
    }

    @Test
    void testConstructorWithException() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> new KitImpl("Test", null, 12, false),
                "The max size for the HotBar is 9"
        );
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
    void testSetArmorItems() {
        var kit = new KitImpl("Test", null, 9, false);
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> kit.setArmorItem(12, dummyItem),
                "The index is to high"
        );
        kit.setItem(0, new Item(ItemStack.builder(Material.STONE).build()));
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