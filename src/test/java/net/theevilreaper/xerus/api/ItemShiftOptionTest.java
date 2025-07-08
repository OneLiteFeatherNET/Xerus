package net.theevilreaper.xerus.api;

import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.theevilreaper.xerus.api.mocks.TestItemShiftOption;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MicrotusExtension.class)
class ItemShiftOptionTest {

    @Test
    void testItemShiftOption(@NotNull Env env) {
        ItemShiftOption shiftOption = new TestItemShiftOption(ItemStack.builder(Material.ACACIA_BOAT).build());
        final Instance instance = env.createFlatInstance();
        final Player player = env.createPlayer(instance, Pos.ZERO);
        shiftOption.setEquipment(player, Locale.ENGLISH, 2);
        assertNotNull(player.getInventory().getItemStack(2));
        player.remove();
        env.destroyInstance(instance);
    }

    @Test
    void testMultipleItemShiftOptions(@NotNull Env env) {
        ItemShiftOption shiftOption = new TestItemShiftOption(ItemStack.builder(Material.ACACIA_BOAT).build());

        final Instance instance = env.createFlatInstance();
        final Set<Player> players = new HashSet<>();
        for (int i = 0; i <= 2; i++) {
            players.add(env.createPlayer(instance, Pos.ZERO));
        }
        assertEquals(3, players.size());

        int counter = 0;

        Iterator<Player> playerIterator = players.iterator();

        while (playerIterator.hasNext() && counter < 2) {
            shiftOption.setEquipment(playerIterator.next(), 2,3);
            counter++;
        }

        final Player lastPlayer = playerIterator.next();

        shiftOption.setEquipment(lastPlayer, 5);

        assertNotNull(lastPlayer.getInventory().getItemStack(5));
        playerIterator = players.iterator();
        counter = 0;

        while (playerIterator.hasNext() && counter < 2) {
            assertNotNull(lastPlayer.getInventory().getItemStack(2));
            assertNotNull(lastPlayer.getInventory().getItemStack(3));
            counter++;
        }
    }
}
