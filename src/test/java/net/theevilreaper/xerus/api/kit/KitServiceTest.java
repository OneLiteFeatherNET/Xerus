package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class KitServiceTest {

    private static KitService kitService;

    @BeforeAll
    static void init() {
        kitService = KitService.of();
        assertNotNull(kitService);
    }

    @AfterEach
    void tearDown() {
        kitService.clear();
        assertEquals(0, kitService.getKits().size(), "The kit service should not contain any kits");
    }

    @Test
    void testKitAdd() {
        assertTrue(kitService.getKits().isEmpty(), "The kit service should be empty before adding kits");

        kitService.add(new TestKit());

        assertFalse(kitService.getKits().isEmpty(), "The kit service should not be empty after adding kits");
        assertEquals(1, kitService.getKits().size(), "The kit service should contain exactly one kit");
    }

    @Test
    void testKitRemoval() {
        Kit kit = new TestKit();
        kitService.add(kit);

        assertFalse(kitService.getKits().isEmpty(), "The kit service should not be empty after adding kits");
        assertEquals(1, kitService.getKits().size(), "The kit service should contain exactly one kit");

        kitService.remove(kit.key());
        assertTrue(kitService.getKits().isEmpty(), "The kit service should be empty after removing a kit");
    }

    @Test
    void testKitGet() {
        Kit kit = new TestKit();
        kitService.add(kit);

        Optional<Kit> fetchedKit = kitService.getKit(kit.key());
        assertTrue(fetchedKit.isPresent());
        assertEquals(kit, fetchedKit.get());
    }

    @Test
    void testKitGetNonExisting() {
        Optional<Kit> fetchedKit = kitService.getKit(Key.key("xerus", "test_kit"));
        assertFalse(fetchedKit.isPresent());
    }

    @Test
    void testDuplicateKitAddition() {
        assertTrue(kitService.getKits().isEmpty());

        Kit kit1 = new TestKit();
        Kit kit2 = new TestKit();

        kitService.add(kit1);
        assertEquals(1, kitService.getKits().size());

        // Adding kit with same key must replace existing without duplicating size
        kitService.add(kit2);
        assertEquals(1, kitService.getKits().size());
        assertEquals(kit2, kitService.getKit(kit1.key()).orElse(null));
    }

    @Test
    void testRemoveNonExisting() {
        assertFalse(kitService.remove(Key.key("xerus", "non_existing_kit")));
    }

    @Test
    void testClearMultipleKits() {
        kitService.add(new TestKit(Key.key("xerus", "kit_1")));
        kitService.add(new TestKit(Key.key("xerus", "kit_2")));
        assertEquals(2, kitService.getKits().size());

        kitService.clear();
        assertTrue(kitService.getKits().isEmpty());
    }

    @Test
    void testUnmodifiableKitsList() {
        kitService.add(new TestKit());
        var kitsList = kitService.getKits();
        assertThrows(UnsupportedOperationException.class, () -> kitsList.add(new TestKit(Key.key("xerus", "other"))));
    }
}
