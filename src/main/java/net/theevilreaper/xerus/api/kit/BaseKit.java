package net.theevilreaper.xerus.api.kit;

import net.minestom.server.utils.validate.Check;
import net.theevilreaper.aves.item.IItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

import static net.minestom.server.inventory.PlayerInventory.INVENTORY_SIZE;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public abstract class BaseKit implements Kit {

    private IItem icon;
    protected EnumMap<ArmorSlot, IItem> armorItems;
    protected IItem[] items;

    protected BaseKit(boolean armorItems) {
        this.items = new IItem[INVENTORY_SIZE];
        if (armorItems) {
            this.armorItems = new EnumMap<>(ArmorSlot.class);
        }
    }

    @Override
    public void setArmorItem(@NotNull ArmorSlot slot, @NotNull IItem item) {
        this.armorItems.put(slot, item);
    }

    @Override
    public void setArmorItems(@NotNull EnumMap<ArmorSlot, IItem> armorItems) {
        this.armorItems = armorItems;
    }

    @Override
    public void setItem(int index, @NotNull IItem item) {
        Check.argCondition(index > items.length,  "The index is to high");
        items[index] = item;
    }

    @Override
    public void setItems(IItem[] items) {
        Check.argCondition(items.length != this.items.length, "The given array has not the same index (" + INVENTORY_SIZE + ")");
        this.items = items;
    }

    @Override
    public void setIcon(@NotNull IItem icon) {
        this.icon = icon;
    }

    /**
     * Returns the underlying icon from the kit.
     * @return The underlying icon which is an object from {@link IItem}
     */
    @Override
    public @Nullable IItem getIcon() {
        return icon;
    }
}
