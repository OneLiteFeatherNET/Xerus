package de.icevizion.xerus.api.kit;

import at.rxcki.strigiformes.MessageProvider;
import at.rxcki.strigiformes.TranslatedObjectCache;
import de.icevizion.aves.item.IItem;
import de.icevizion.aves.util.Players;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * The {@link TranslatedKit} is an implementation from the {@link Kit} which supports the translation over i18n.
 * The kit handles the items for the armor slots and the hotBar itself.
 * If other items should be added, this must be implemented explicitly (changes can still be made).
 * When creating an instance of the kit, arrays are pre-generated for the items
 * However, the developer must place the items in them independently
 *
 * The array for the armor items has an maximum length of four
 * The array for the hotBar items has an maximum length of nine
 *
 * With a new backend for the items, the kit supports these use cases:
 * - Kits that are completely translated
 * - Kits where names and descriptions are translated but the items are not.
 * - Kits that use a structure that provides translated items and non-translated items
 *
 * Note: The option to use a description for the kits is optional
 *
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public class TranslatedKit implements Kit {

    protected final MessageProvider messageProvider;
    private final TranslatedObjectCache<String> name;
    private final String nameKey;
    private TranslatedObjectCache<String> description;
    protected IItem icon;
    protected IItem[] armorItems;
    protected IItem[] hotBarItems;

    /**
     * Creates a new instance from the {@link TranslatedKit} with the given value.
     * @param messageProvider A valid instance to an {@link MessageProvider}
     * @param nameKey The translation key for the name
     * @param hotBarSize The size of the items in the hotBar
     * @param armorItems Option to use the armor slots
     */
    public TranslatedKit(MessageProvider messageProvider, String nameKey, int hotBarSize, boolean armorItems) {
        this.nameKey = nameKey;
        Check.argCondition(hotBarSize > 9, "The maximum size for the Hotbar is nine");
        this.messageProvider = messageProvider;
        this.hotBarItems = new IItem[hotBarSize];

        if (armorItems) {
            this.armorItems = new IItem[4];
        }

        this.name = new TranslatedObjectCache<>(locale -> messageProvider.getTextProvider().getString(nameKey, locale));
    }

    /**
     * Creates a new instance from the {@link TranslatedKit} with the given value.
     * @param messageProvider A valid instance to an {@link MessageProvider}
     * @param name The translation key for the name
     * @param hotBarSize The size of the items in the hotBar
     * @param armorItems Option to use the armor slots
     * @return The created object from the class
     */
    @Contract("_, _, _, _ -> new")
    public static @NotNull TranslatedKit of(MessageProvider messageProvider, int hotBarSize, String name, boolean armorItems) {
        return new TranslatedKit(messageProvider, name, hotBarSize, armorItems);
    }

    /**
     * Sets the description key for the description from the kit.
     * @param descriptionKey The key to set which represents the description.
     */
    public void setDescription(String descriptionKey) {
        this.description = new TranslatedObjectCache<>(locale ->
                messageProvider.getTextProvider().getString(descriptionKey, locale));
    }

    /**
     * Add an item to a specific position in the array for the armor items.
     * @param index The index where the item should be set
     * @param item The item to set
     */
    @Override
    public void setArmorItem(int index, @NotNull IItem item) {
        if (index > armorItems.length) {
            throw new IllegalArgumentException("The index is higher than the length of the array. " +
                    "The length is" + armorItems.length);
        }
        this.armorItems[index] = item;
    }

    @Override
    public void setArmorItems(@NotNull IItem... armorItems) {
        if (armorItems.length > MAX_ARMOR_ITEMS) {
            throw new IllegalArgumentException("The given max size for the armor array is four. " +
                    "The given length is " + armorItems.length);
        }
        this.armorItems = armorItems;
    }

    /**
     * Add an item to a specific position in the array for the hotBar items.
     * @param index The index where the item should be set
     * @param item The item to set
     */
    @Override
    public void setItem(int index, @NotNull IItem item) {
        if (index > hotBarItems.length) {
            throw new IllegalArgumentException("The index is higher than the length of the array. " +
                    "The length is" + hotBarItems.length);
        }

        this.hotBarItems[index] = item;
    }

    @Override
    public void setItems(@NotNull IItem... items) {
        if (items.length > MAX_HOT_BAR_ITEMS) {
            throw new IllegalArgumentException("The given max size for the hotbar array is nine. " +
                    "The given length is " + armorItems.length);
        }
        this.hotBarItems = items;
    }

    /**
     * Sets the icon for the kit.
     * @param icon The icon to set
     */
    public void setIcon(IItem icon) {
        this.icon = icon;
    }

    /**
     * Gives the kit to the player in his inventory.
     * @param player The player who receives the kit
     * @param shiftedSlots The slot array where the indexes can be shifted
     */
    @Override
    public void setEquipment(@NotNull Player player, Locale locale, int... shiftedSlots) {
        Players.updateEquipment(player, armorItems, hotBarItems, locale, shiftedSlots);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslatedKit that = (TranslatedKit) o;
        return nameKey.equals(that.nameKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameKey);
    }

    @Override
    public String getIdentifier() {
        return nameKey;
    }

    /**
     * Calls an exception because the class has translated usage.
     * @return a exception
     */
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Locale needed");
    }

    /**
     * Get the name from the kit. If the locale is null the method returns the nameKey from the kit
     * @param locale The locale to determine the right kit item
     * @return The fetched name on the basis of the locale
     */
    @Override
    public String getName(Locale locale) {
        return name.get(locale);
    }

    /**
     * Gets the description from the kit.
     * @param locale The locale to determine the right kit item
     * @return The fetched name on the basis of the locale
     */
    @Override
    public String getDescription(Locale locale) {
        return description.get(locale);
    }

    /**
     * Returns the underlying icon from the kit.
     * @return The underlying icon which is an object from {@link IItem}
     */
    public @Nullable IItem getIcon() {
        return icon;
    }
}