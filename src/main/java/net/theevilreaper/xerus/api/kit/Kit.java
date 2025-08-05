package net.theevilreaper.xerus.api.kit;

import net.theevilreaper.xerus.api.ItemShiftOption;
import net.kyori.adventure.text.Component;
import net.theevilreaper.aves.item.IItem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.EnumMap;
import java.util.Locale;

/**
 * The {@link Kit} interface provides all basic methods that a kit should have.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public interface Kit extends ItemShiftOption {

    /**
     * The maximum number of armor items that can be set.
     */
    int MAX_ARMOR_ITEMS = 4;

    /**
     * Creates a new instance of the kit.
     *
     * @param name       The name of the kit
     * @param armorItems Whether the kit should contain armor items
     * @return the created instance
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull KitImpl of(@NotNull Component name, boolean armorItems) {
        return new KitImpl(name, null, armorItems);
    }

    /**
     * Set the icon for the kit.
     *
     * @param item the item to set
     */
    void setIcon(@NotNull IItem item);

    /**
     * Add an item to a specific position in the array for the armor items.
     *
     * @param slot The index where the item should be set
     * @param item The item to set
     */
    void setArmorItem(@NotNull ArmorSlot slot, @NotNull IItem item);

    /**
     * Set the armor items directly per array.
     *
     * @param items The items to set
     */
    void setArmorItems(@NotNull EnumMap<ArmorSlot, IItem> items);

    /**
     * Add an item to a specific position in the array for the hotBar items.
     *
     * @param index The index where the item should be set
     * @param item  The item to set
     */
    void setItem(int index, @NotNull IItem item);

    /**
     * Set all hotbar items directly per array.
     *
     * @param items The items to set
     */
    void setItems(@NotNull IItem... items);

    /**
     * Returns the identifier of the kit.
     *
     * @return the underlying value
     */
    String getIdentifier();

    /**
     * Gets the name of the kit.
     *
     * @return the underlying value
     */
    default Component getName() {
        return getName(null);
    }

    /**
     * Returns the name of the kit.
     *
     * @return the underlying value
     */
    Component getName(@UnknownNullability Locale locale);

    /**
     * Returns the description from the kit.
     *
     * @return the underlying value
     */
    default Component getDescription() {
        return getDescription(null);
    }

    /**
     * Returns the description from the kit.
     *
     * @param locale The locale to determine the right description
     * @return The underlying value
     */
    Component getDescription(@UnknownNullability Locale locale);

    /**
     * Returns the icon from the kit.
     *
     * @return the underlying value
     */
    @Nullable
    IItem getIcon();
}
