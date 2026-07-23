package net.theevilreaper.xerus.api;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * The interface provides some method to add a single player object or a collection of players.
 * Each developer can implement the interface into his class but must write his own logic for the methods.
 *
 * @author theEvilReaper
 * @version 1.2.0
 * @since 1.0.0
 */
public interface Joinable {

    /**
     * Add a single player
     * @param player The player to add
     */
    default void addPlayer(Player player) {
        this.addPlayer(player, null);
    }

    /**
     * Add a single {@link Player} entry to a structure.
     * @param player the player to add
     * @param consumer a consumer which is called to execute some logic
     */
    void addPlayer(Player player, @Nullable Consumer<Player> consumer);

    /**
     * Add a collection of players
     * @param players The collection which contains the players to add
     */
    default void addPlayers(Collection<Player> players) {
        this.addPlayers(players, null);
    }

    /**
     * Add a collection of players
     * @param players The collection which contains the players to add
     * @param consumer a consumer which is called to execute some logic
     */
    void addPlayers(Collection<Player> players, @Nullable Consumer<Player> consumer);

    /**
     * Remove a single player
     * @param player The player to remove
     */
    default void removePlayer(Player player) {
        this.removePlayer(player, null);
    }

    /**
     * Remove a single {@link Player} entry from a structure.
     * @param player the player to remove
     * @param consumer a consumer which is called to execute some logic
     */
    void removePlayer(Player player, @Nullable Consumer<Player> consumer);

    /**
     * Remove a collection of players
     * @param players The collection which contains the players to remove
     */
    default void removePlayers(Collection<Player> players) {
        this.removePlayers(players, null);
    }

    /**
     * Remove a collection of players
     * @param players The collection which contains the players to remove
     * @param consumer a consumer which is called to execute some logic
     */
    void removePlayers(Collection<Player> players, @Nullable Consumer<Player> consumer);
}