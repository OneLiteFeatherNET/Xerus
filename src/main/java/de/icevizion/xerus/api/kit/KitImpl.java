package de.icevizion.xerus.api.kit;

import de.icevizion.aves.item.IItem;
import de.icevizion.aves.util.Players;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public class KitImpl implements Kit {

    private final String name;
    private final String description;
    private IItem icon;
    protected IItem[] armorItems;
    protected IItem[] hotBarItems;

    public KitImpl(@NotNull String name, @Nullable String description, int hotBarSize, boolean armorItems) {
        Check.argCondition(hotBarSize > MAX_HOT_BAR_ITEMS, "The max size for the HotBar is 9");
        this.name = name;
        this.description = description;
        this.hotBarItems = new IItem[hotBarSize];

        if (armorItems) {
            this.armorItems = new IItem[MAX_ARMOR_ITEMS];
        }
    }

    public void setArmorItem(int index, @NotNull IItem item) {
        Check.argCondition(index > hotBarItems.length, "The index is to high");
        armorItems[index] = item;
    }

    @Override
    public void setArmorItems(@NotNull IItem... armorItems) {
        Check.argCondition(armorItems.length > MAX_ARMOR_ITEMS,
                "The given array is greater than four");
        this.armorItems = armorItems;
    }

    @Override
    public void setItem(int index, @NotNull IItem item) {
        Check.argCondition(index > hotBarItems.length,  "The index is to high");
        hotBarItems[index] = item;
    }

    @Override
    public void setItems(@NotNull IItem... items) {
        if (items.length > MAX_HOT_BAR_ITEMS) {
            throw new IllegalArgumentException("The given max size for the hotbar array is nine. " +
                    "The given length is " + armorItems.length);
        }
        this.hotBarItems = items;
    }

    @Override
    public void setEquipment(@NotNull Player player, Locale locale, int... shiftedSlots) {
        Players.updateEquipment(player, armorItems, hotBarItems, locale, shiftedSlots);
    }

    /**
     * Set the icon of the kit.
     *
     * @param icon The {@link IItem} to set
     */
    public void setIcon(IItem icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KitImpl kit = (KitImpl) o;
        return Objects.equals(name, kit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns the icon of the kit
     *
     * @return The icon as {@link net.minestom.server.item.ItemStack}
     */
    @Nullable
    public IItem getIcon() {
        return icon;
    }

    /**
     * Returns the name from the kit.
     * @param ignored can be ignored because the kits is not translated
     * @return the given name from the kit
     */
    @Override
    public String getName(@Nullable Locale ignored) {
       return name;
    }

    /**
     * Returns the description from the kit.
     * @param ignored can be ignored because the kit is not translated
     * @return the given description as string
     */
    @Override
    public String getDescription(@Nullable Locale ignored) {
        return description;
    }

    /**
     * Returns the identifier from the kit.
     * In the non translated context the method returns the same result as getName() method;
     * @return the given identifier
     */
    @Override
    public String getIdentifier() {
        return getName(null);
    }
}