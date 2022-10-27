package de.icevizion.xerus.api.kit;

import de.icevizion.aves.item.IItem;
import de.icevizion.xerus.api.Equipable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Locale;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public interface Kit extends Equipable {

    int MAX_ARMOR_ITEMS = 4;
    int MAX_HOT_BAR_ITEMS = 9;

    @Contract(value = "_, _, _ -> new", pure = true)
    static @NotNull KitImpl of(@NotNull String name, int hotBarSize, boolean armorItems) {
        return new KitImpl(name, null, hotBarSize, armorItems);
    }

    /**
     * Add an item to a specific position in the array for the armor items.
     * @param index The index where the item should be set
     * @param item The item to set
     */
    void setArmorItem(int index, @NotNull IItem item);

    /**
     * Set the armor items directly per array.
     * @param items The items to set
     */
    void setArmorItems(@NotNull IItem... items);

    /**
     * Add an item to a specific position in the array for the hotBar items.
     * @param index The index where the item should be set
     * @param item The item to set
     */
    void setItem(int index, @NotNull IItem item);

    /**
     * Set all hotbar items directly per array.
     * @param items The items to set
     */
    void setItems(@NotNull IItem... items);

    /**
     * Returns the identifier of the kit.
     * @return the underlying value
     */
    String getIdentifier();

    /**
     * Gets the name of the kit.
     * @return the underlying value
     */
    default String getName() { return getName(null); }

    /**
     * Returns the name of the kit.
     * @return the underlying value
     */
    String getName(@UnknownNullability Locale locale);

    /**
     * Returns the description from the kit.
     * @return the underlying value
     */
    default String getDescription() { return getDescription(null); }

    /**
     * Returns the description from the kit.
     * @param locale The locale to determine the right description
     * @return The underlying value
     */
    String getDescription(@UnknownNullability Locale locale);

    /**
     * Returns the icon from the kit.
     * @return the underlying value
     */
    @Nullable
    IItem getIcon();
}
