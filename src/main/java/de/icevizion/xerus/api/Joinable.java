package de.icevizion.xerus.api;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * The interface provides some method to add a single player object or a set off players.
 * Each developer can implement the interface into his class but must write his own logic for the methods.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Joinable {

    /**
     * Add a single player
     * @param paramPlayer The player to add
     */
    void addPlayer(@NotNull Player paramPlayer);

    /**
     * Add a set off players
     * @param players The set off player to add
     */
    void addPlayers(@NotNull Set<Player> players);

    /**
     * Remove a single player
     * @param paramPlayer The player to remove
     */
    void removePlayer(@NotNull Player paramPlayer);

    /**
     * Remove a set off players
     * @param players The set off player to remove
     */
    void removePlayers(@NotNull Set<Player> players);
}