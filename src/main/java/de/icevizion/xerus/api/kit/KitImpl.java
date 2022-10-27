package de.icevizion.xerus.api.kit;

import de.icevizion.aves.item.IItem;
import de.icevizion.aves.util.Players;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Objects;

import static net.minestom.server.inventory.PlayerInventory.*;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public class KitImpl extends BaseKit {

    private final String name;
    private final String description;
    public KitImpl(@NotNull String name, @Nullable String description, boolean armorItems) {
        super(armorItems);
        this.name = name;
        this.description = description;
    }

    @Override
    public void setEquipment(@NotNull Player player, Locale locale, int... shiftedSlots) {
        Players.updateEquipment(player, armorItems.values().toArray(IItem[]::new), items, locale, shiftedSlots);
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