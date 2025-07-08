package net.theevilreaper.xerus.api.mocks;

import net.theevilreaper.xerus.api.ItemShiftOption;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public final class TestItemShiftOption implements ItemShiftOption {

    private final ItemStack itemStack;

    public TestItemShiftOption(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void setEquipment(@NotNull Player player, @Nullable Locale locale, int... shiftedSlots) {
        //TODO: Throw an exception when given array is empty
        if (shiftedSlots.length == 0) {
            player.getInventory().setItemStack(0, itemStack);
            return;
        }
        for (int i = 0; i < shiftedSlots.length; i++) {
            player.getInventory().setItemStack(shiftedSlots[i], itemStack);
        }
    }
}
