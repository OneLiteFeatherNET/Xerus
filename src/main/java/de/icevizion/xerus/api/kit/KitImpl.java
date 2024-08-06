package de.icevizion.xerus.api.kit;

import de.icevizion.aves.item.IItem;
import de.icevizion.aves.util.Players;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public class KitImpl extends BaseKit {

    private final Component name;
    private final Component description;

    public KitImpl(@NotNull Component name, @Nullable Component description, boolean armorItems) {
        super(armorItems);
        this.name = name;
        this.description = description;
    }

    @Override
    public void setEquipment(@NotNull Player player, Locale locale, int... shiftedSlots) {
        Players.updateArmorItems(player, armorItems.values().toArray(IItem[]::new), locale);
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
     * Returns the name from the kit.
     * @param ignored can be ignored because the kits is not translated
     * @return the given name from the kit
     */
    @Override
    public Component getName(@Nullable Locale ignored) {
       return name;
    }

    /**
     * Returns the description from the kit.
     * @param ignored can be ignored because the kit is not translated
     * @return the given description as string
     */
    @Override
    public Component getDescription(@Nullable Locale ignored) {
        return description;
    }

    /**
     * Returns the identifier from the kit.
     * In the non translated context the method returns the same result as getName() method;
     * @return the given identifier
     */
    @Override
    public String getIdentifier() {
        return PlainTextComponentSerializer.plainText().serialize(getName(null));
    }
}