package de.icevizion.xerus.api.mocks;

import de.icevizion.xerus.api.ItemShiftOption;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public final class TestItemShiftOption implements ItemShiftOption {

    private static final ItemStack TEST_ITEM = ItemStack.builder(Material.BAMBOO_BUTTON).build();

    @Override
    public void setEquipment(@NotNull Player player, @Nullable Locale locale, int... shiftedSlots) {
        //TODO: Throw an exception when given array is empty
        if (shiftedSlots.length == 0) {
            player.getInventory().setItemStack(0, TEST_ITEM);
            return;
        }
        for (int i = 0; i < shiftedSlots.length; i++) {
            player.getInventory().setItemStack(shiftedSlots[i], TEST_ITEM);
        }
    }
}
