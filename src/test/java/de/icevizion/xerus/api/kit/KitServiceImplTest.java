package de.icevizion.xerus.api.kit;

import net.minestom.server.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KitServiceImplTest {

    KitServiceImpl kitService;

    Kit defaultKit;

    Player testPlayer;

    @BeforeAll
    void init() {
        this.testPlayer = Mockito.mock(Player.class);
        this.defaultKit = new KitImpl("TestKit", "TestKit", 3, true);
        this.kitService = new KitServiceImpl();
        this.kitService.add(defaultKit);
    }

    @Order(1)
    @Test
    void testOtherConstructor() {
        var service = new KitServiceImpl(10);
        assertSame(0, service.getKits().size());
    }

    @Order(2)
    @Test
    void testAddKit() {
        this.kitService.add(new KitImpl("Test", "Test", 3, false));
        assertSame(2, this.kitService.getKits().size());
    }

    @Order(3)
    @Test
    void testAddKitWithPlayer() {
        this.kitService.add(testPlayer, defaultKit);
        assertSame(1, this.kitService.getUsedKits().size());
    }

    @Order(4)
    @Test
    void testRemoveKitFromPlayer() {
        assertNotNull(this.kitService.remove(testPlayer));
    }

    @Order(5)
    @Test
    void testRemoveKit() {
        assertFalse(this.kitService.remove("SuperDuperKit"));
    }

    @Order(6)
    @Test
    void testFailRemove() {
        assertThrowsExactly(IllegalArgumentException.class, () -> this.kitService.remove(" "), "The identifier can not be empty");
    }

    @Order(7)
    @Test
    void testGetKit() {
        var optionalKit = this.kitService.getKit("TestKit");
        assertTrue(optionalKit.isPresent());
    }

    @Order(8)
    @Test
    void testFailGetKit() {
        var optionalKit = this.kitService.getKit(" ");
        assertThrowsExactly(IllegalArgumentException.class, () -> this.kitService.remove(" "), "The name can not be empty");
        assertFalse(optionalKit.isPresent());
    }

    @Order(9)
    @Test
    void testNullGetKit() {
        var optionalKit = this.kitService.getKit("SuperDuperKit");
        assertThrowsExactly(NoSuchElementException.class, optionalKit::get, "No value present");
    }

    @Order(10)
    @Test
    void testGetKitByPlayer() {
        var kitOptional = this.kitService.getKit(testPlayer);
        assertFalse(kitOptional.isPresent());
    }

    @Order(11)
    @Test
    void testGetKits() {
        assertSame(2, this.kitService.getKits().size());
    }

    @Order(12)
    @Test
    void testUsedKits() {
        assertSame(0, this.kitService.getUsedKits().size());
    }

    @Order(13)
    @Test
    void removeTestKit() {
        assertTrue(this.kitService.remove(defaultKit));
    }

    @Order(14)
    @Test
    void testClearCachedKits() {
        this.kitService.invalidatePlayerCache();
        assertSame(0, this.kitService.getUsedKits().size());
    }

    @Order(15)
    @Test
    void testClearService() {
        this.kitService.clear();
        assertSame(0, this.kitService.getKits().size());
    }
}