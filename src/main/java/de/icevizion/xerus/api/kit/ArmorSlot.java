package de.icevizion.xerus.api.kit;

import net.minestom.server.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public enum ArmorSlot {

    BOOTS(EquipmentSlot.BOOTS),
    LEGGINGS(EquipmentSlot.LEGGINGS),
    CHESTPLATE(EquipmentSlot.CHESTPLATE),
    HELMET(EquipmentSlot.HELMET);

    final EquipmentSlot slot;

    ArmorSlot(@NotNull EquipmentSlot slot) {
        this.slot = slot;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }
}
