package net.theevilreaper.xerus.api.kit;

import net.minestom.server.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link ArmorSlot} enum contains all available armor slots.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public enum ArmorSlot {

    /**
     * Represents the helmet slot.
     */
    HELMET(EquipmentSlot.HELMET),
    /**
     * Represents the chestplate slot.
     */
    CHESTPLATE(EquipmentSlot.CHESTPLATE),
    /**
     * Represents the legging's slot.
     */
    LEGGINGS(EquipmentSlot.LEGGINGS),
    /**
     * Represents the boots slot.
     */
    BOOTS(EquipmentSlot.BOOTS);

    final EquipmentSlot slot;

    /**
     * Creates a new {@link ArmorSlot} with the given equipment slot.
     * @param slot the equipment slot to use.
     */
    ArmorSlot(@NotNull EquipmentSlot slot) {
        this.slot = slot;
    }

    /**
     * Returns the underlying equipment slot.
     *
     * @return the slot
     */
    public @NotNull EquipmentSlot getSlot() {
        return slot;
    }
}
