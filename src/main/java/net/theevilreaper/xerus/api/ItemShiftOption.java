package net.theevilreaper.xerus.api;

import net.minestom.server.entity.Player;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * Contains some methods that help to set the player items.
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
@FunctionalInterface
public interface ItemShiftOption {

    /**
     * Sets an equipment to a specific player.
     * @param player the player who receives the equipment
     * @param locale the locale to determine the right items
     * @param shiftedSlots specifies whether the items should be added in a different order
     */
    void setEquipment(@NotNull Player player, Locale locale, int... shiftedSlots);

    /**
     * Sets an equipment to an specific player.
     * @param player the player who receives the equipment
     * @param shiftedSlots array containing shifted slots for the items
     */
    default void setEquipment(@NotNull Player player, int... shiftedSlots) {
        Check.argCondition(player.getInstance() == null, "The instance from a player can not be null");
        this.setEquipment(player, null, shiftedSlots);
    }

    /**
     * Sets an equipment to an specific player.
     * @param players the players who receive the equipment
     * @param shiftedSlots array containing shifted slots for the items
     */
    default void setEquipment(@NotNull List<Player> players, int... shiftedSlots) {
        if (players.isEmpty()) return;

        for (int i = 0; i < players.size(); i++) {
            this.setEquipment(players.get(i), shiftedSlots);
        }
    }
}
