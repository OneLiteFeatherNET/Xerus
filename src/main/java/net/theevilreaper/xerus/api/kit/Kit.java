package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import net.theevilreaper.xerus.api.ItemShiftOption;
import net.theevilreaper.xerus.api.component.Componentable;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Kit} interface provides all basic methods that a kit should have.
 *
 * @author theEvilReaper
 * @version 2.0.0
 * @since 1.2.0
 **/
public interface Kit extends ItemShiftOption, Componentable {

    /**
     * Applies the kit to a player.
     *
     * @param player the player to apply the kit to
     */
    void apply(@NotNull Player player);

    /**
     * Returns the identifier of the kit.
     *
     * @return the underlying value
     */
    @NotNull Key key();
}
