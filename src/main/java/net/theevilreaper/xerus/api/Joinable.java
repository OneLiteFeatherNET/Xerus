package net.theevilreaper.xerus.api;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;

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
    default void addPlayer(@NotNull Player paramPlayer) {
        this.addPlayer(paramPlayer, null);
    }

    /**
     * Add a single {@link Player} entry to a structure.
     * @param paramPlayer the player to add
     * @param consumer a consumer which is called to execute some logic
     */
    void addPlayer(@NotNull Player paramPlayer, @Nullable Consumer<Player> consumer);

    /**
     * Add a set off players
     * @param players The set which contains the players to add
     */
    default void addPlayers(@NotNull Set<Player> players) {
        this.addPlayers(players, null);
    }

    /**
     * Add a set off players
     * @param players The set which contains the players to add
     * @param consumer a consumer which is called to execute some logic
     */
    void addPlayers(@NotNull Set<Player> players, @Nullable Consumer<Player> consumer);

    /**
     * Remove a single player
     * @param paramPlayer The player to remove
     */
    default void removePlayer(@NotNull Player paramPlayer) {
        this.removePlayer(paramPlayer, null);
    }

    /**
     * Remove a single {@link Player} entry from a structure.
     * @param paramPlayer the player to remove
     * @param consumer a consumer which is called to execute some logic
     */
    void removePlayer(@NotNull Player paramPlayer, @Nullable Consumer<Player> consumer);

    /**
     * Remove a set off players
     * @param players The set which contains the players to remove
     */
    default void removePlayers(@NotNull Set<Player> players) {
        this.removePlayers(players, null);
    }

    /**
     * Remove a set off players
     * @param players The set which contains the players to remove
     * @param consumer a consumer which is called to execute some logic
     */
    void removePlayers(@NotNull Set<Player> players, @Nullable Consumer<Player> consumer);
}