package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KitServiceImplTest {

    KitServiceImpl kitService;

    Kit defaultKit;

    @BeforeAll
    void init() {
        final Component name = Component.text("TestKit");
        this.defaultKit = new KitImpl(name, name, true);
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
        final var name = Component.text("Test");
        this.kitService.add(new KitImpl(name, name, false));
        assertSame(2, this.kitService.getKits().size());
    }

    @Order(3)
    @Test
    void testAddKitWithPlayer(Env env) {
        final Instance instance = env.createFlatInstance();
        final Player testPlayer = env.createPlayer(instance, Pos.ZERO);
        this.kitService.add(testPlayer, defaultKit);
        assertSame(1, this.kitService.getUsedKits().size());
    }

    @Order(4)
    @Test
    void testRemoveKitFromPlayer(Env env) {
        final Instance instance = env.createFlatInstance();
        final Player testPlayer = env.createPlayer(instance, Pos.ZERO);
        this.kitService.add(testPlayer, defaultKit);
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

    /*@Order(10)
    @Test
    void testGetKitByPlayer() {
        var kitOptional = this.kitService.getKit(testPlayer);
        assertFalse(kitOptional.isPresent());
    }*/

    @Order(11)
    @Test
    void testGetKits() {
        assertSame(2, this.kitService.getKits().size());
    }

    @Order(12)
    @Test
    void testUsedKits() {
        assertSame(1, this.kitService.getUsedKits().size());
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