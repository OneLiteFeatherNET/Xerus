package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import net.theevilreaper.xerus.api.component.Componentable;

/**
 * The {@link Kit} interface provides all basic methods that a kit should have.
 *
 * @author theEvilReaper
 * @version 2.1.0
 * @since 1.2.0
 **/
public interface Kit extends Componentable {

    /**
     * Applies the kit to a player.
     *
     * @param player the player to apply the kit to
     */
    void apply(Player player);

    /**
     * Returns the identifier of the kit.
     *
     * @return the underlying value
     */
    Key key();
}
