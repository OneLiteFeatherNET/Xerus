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
}
