package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.theevilreaper.aves.item.IItem;
import net.theevilreaper.aves.item.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;

import static net.theevilreaper.xerus.api.kit.Kit.MAX_ARMOR_ITEMS;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KitTest {

    static final Component NAME = Component.text("TestKit");

    private IItem dummyItem;
    private Kit kit;

    @BeforeAll
    void init() {
        this.dummyItem = Item.of(ItemStack.AIR);
        this.kit = Kit.of(NAME, true);
    }

    @Test
    void testMaxArmorItems() {
        assertSame(4, MAX_ARMOR_ITEMS);
    }

    @Test
    void testSetArmorItems() {
        var testKit = new KitImpl(NAME, null, false);
        assertThrows(NullPointerException.class, () -> testKit.setArmorItem(ArmorSlot.BOOTS, dummyItem));
        testKit.setItem(0, Item.of(ItemStack.builder(Material.STONE).build()));
        assertNotNull(testKit);
    }

    @Test
    void testGetIdentifier() {
        assertEquals("TestKit", this.kit.getIdentifier());
    }

    @Test
    void testGetName() {
        assertEquals("TestKit", PlainTextComponentSerializer.plainText().serialize(this.kit.getName()));
    }

    @Test
    void testGetNameWithLocale() {
        assertEquals("TestKit", PlainTextComponentSerializer.plainText().serialize(this.kit.getName(Locale.ENGLISH)));
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