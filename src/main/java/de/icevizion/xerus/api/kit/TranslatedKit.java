package de.icevizion.xerus.api.kit;

import de.icevizion.aves.i18n.TextData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * The {@link TranslatedKit} is an implementation from the {@link Kit} which supports the translation over i18n.
 * The kit handles the items for the armor slots and the hotBar itself.
 * If other items should be added, this must be implemented explicitly (changes can still be made).
 * When creating an instance of the kit, arrays are pre-generated for the items
 * However, the developer must place the items in them independently
 * <p>
 * The array for the armor items has a maximum length of four
 * The array for the hotBar items has a maximum length of nine
 * <p>
 * With a new backend for the items, the kit supports these use cases:
 * - Kits that are completely translated
 * - Kits where names and descriptions are translated but the items are not.
 * - Kits that use a structure that provides translated items and non-translated items
 * <p>
 * Note: The option to use a description for the kits is optional
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public class TranslatedKit extends BaseKit {

    private final Map<Locale, Component> name;
    private final String nameKey;
    private TextData description;

    /**
     * Creates a new instance from the {@link TranslatedKit} with the given value.
     *
     * @param nameKey    The translation key for the name
     * @param armorItems Option to use the armor slots
     */
    public TranslatedKit(@NotNull String nameKey, boolean armorItems) {
        super(armorItems);
        this.nameKey = nameKey;
        this.name = new HashMap<>();
    }

    /**
     * Creates a new instance from the {@link TranslatedKit} with the given value.
     *
     * @param name       The translation key for the name
     * @param armorItems Option to use the armor slots
     * @return The created object from the class
     */
    @Contract("_, _ -> new")
    public static @NotNull TranslatedKit of(String name, boolean armorItems) {
        return new TranslatedKit(name, armorItems);
    }

    /**
     * Sets the description key for the description from the kit.
     *
     * @param description the key for the description
     */
    public void setDescription(TextData description) {
        this.description = description;
    }

    /**
     * Gives the kit to the player in his inventory.
     *
     * @param player       The player who receives the kit
     * @param shiftedSlots The slot array where the indexes can be shifted
     */
    @Override
    public void setEquipment(@NotNull Player player, Locale locale, int... shiftedSlots) {
        // Players.updateEquipment(player, armorItems, items, locale, shiftedSlots);
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
     *
     * @return a exception
     */
    @Override
    public Component getName() {
        throw new UnsupportedOperationException("Locale needed");
    }

    /**
     * Get the name from the kit. If the locale is null the method returns the nameKey from the kit
     *
     * @param locale The locale to determine the right kit item
     * @return The fetched name on the basis of the locale
     */
    @Override
    public Component getName(Locale locale) {
        return this.name.computeIfAbsent(locale, locale1 -> GlobalTranslator.render(Component.translatable(nameKey), locale1));
    }

    /**
     * Gets the description from the kit.
     *
     * @param locale The locale to determine the right kit item
     * @return The fetched name on the basis of the locale
     */
    @Override
    public Component getDescription(Locale locale) {
        return GlobalTranslator.render(Component.translatable(description.key(), description.args()), locale);
    }
}