package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.theevilreaper.xerus.api.Joinable;
import net.theevilreaper.xerus.api.component.Componentable;
import net.theevilreaper.xerus.api.team.event.MultiPlayerTeamEvent;
import net.theevilreaper.xerus.api.team.event.PlayerTeamEvent;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventDispatcher;
import net.theevilreaper.xerus.api.team.event.TeamAction;
import org.jetbrains.annotations.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The Team interface represents a blueprint for managing a team or a similar group in a software application.
 * It defines essential methods for organizing and interacting with a team of players or members.
 *
 * @author theEvilReaper
 * @version 2.2.0
 * @since 1.1.6
 **/
public interface Team extends Joinable, Componentable, Comparator<Team> {

    /**
     * Creates a new {@link Team} instance with the given key.
     *
     * @param key the key of the team
     * @return the created team
     */
    @Contract("_ -> new")
    static Team of(Key key) {
        return new DefaultTeam(key, -1);
    }

    /**
     * Creates a new {@link Team} instance with the given key and capacity.
     *
     * @param key      the key of the team
     * @param capacity the capacity of the team
     * @return the created team
     */
    @Contract("_, _ -> new")
    static Team of(Key key, int capacity) {
        return new DefaultTeam(key, capacity);
    }

    /**
     * Add a single {@link Player} entry to a structure.
     *
     * @param player   the player to add
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void addPlayer(@NotNull Player player, @Nullable Consumer<Player> consumer) {
        if (!canJoin()) return;
        PlayerTeamEvent teamEvent = new PlayerTeamEvent(player, this, TeamAction.ADD);
        EventDispatcher.callCancellable(teamEvent, () -> {
            if (getPlayers().add(player) && consumer != null) {
                consumer.accept(player);
            }
        });
    }

    /**
     * Add certain players to a team.
     *
     * @param players  the set with the players to add
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void addPlayers(Collection<Player> players, @Nullable Consumer<Player> consumer) {
        if (players.isEmpty()) return;
        Set<Player> toAdd = new HashSet<>(players);
        toAdd.removeIf(this::hasPlayer);
        if (toAdd.isEmpty()) return;
        EventDispatcher.callCancellable(new MultiPlayerTeamEvent(this, toAdd, TeamAction.ADD), () -> {
            for (Player player : toAdd) {
                if (canJoin() && getPlayers().add(player) && consumer != null) {
                    consumer.accept(player);
                }
            }
        });
    }

    /**
     * Remove a single {@link Player} entry from a structure.
     *
     * @param paramPlayer the player to remove
     * @param consumer    a consumer which is called to execute some logic
     */
    @Override
    default void removePlayer(Player paramPlayer, @Nullable Consumer<Player> consumer) {
        PlayerTeamEvent teamEvent = new PlayerTeamEvent(paramPlayer, this, TeamAction.REMOVE);
        EventDispatcher.callCancellable(teamEvent, () -> {
            if (getPlayers().remove(paramPlayer) && consumer != null) {
                consumer.accept(paramPlayer);
            }
        });
    }

    /**
     * Remove certain players from a team.
     *
     * @param players  The set of players to remove
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void removePlayers(Collection<Player> players, @Nullable Consumer<Player> consumer) {
        if (players.isEmpty()) return;
        Set<Player> toRemove = new HashSet<>(players);
        toRemove.retainAll(getPlayers());
        if (toRemove.isEmpty()) return;
        EventDispatcher.callCancellable(new MultiPlayerTeamEvent(this, toRemove, TeamAction.REMOVE), () -> {
            for (Player player : toRemove) {
                getPlayers().remove(player);
                if (consumer != null) {
                    consumer.accept(player);
                }
            }
        });
    }

    /**
     * Set / overwrite the capacity of the team.
     *
     * @param capacity the capacity to set
     */
    void setCapacity(int capacity);

    /**
     * Checks if a player can join a team or not.
     *
     * @return true when a player can join otherwise false
     */
    boolean canJoin();

    /**
     * Checks if a given player is in the team.
     *
     * @param player the player to check
     * @return true when the player is in the team otherwise false
     */
    default boolean hasPlayer(Player player) {
        return getPlayers().contains(player);
    }

    /**
     * Send a specific message to each player in the team.
     *
     * @param component the message wraps as {@link Component} who should send
     */
    default void sendMessage(Component component) {
        if (isEmpty()) return;
        for (Player player : getPlayers()) {
            player.sendMessage(component);
        }
    }

    /**
     * Clears the underlying structure in which the players are stored.
     */
    default void clearPlayers() {
        if (getPlayers().isEmpty()) return;
        removePlayers(new HashSet<>(getPlayers()), null);
    }

    /**
     * Returns the identifier of the team as {@link Key}.
     *
     * @return the identifier
     */
    Key key();

    /**
     * Returns a boolean indicator if the team contains entries or not.
     *
     * @return true when the team has no entries otherwise false
     */
    default boolean isEmpty() {
        return this.getPlayers().isEmpty();
    }

    /**
     * Returns the maximum capacity of the team.
     *
     * @return -1 when no capacity is set otherwise the capacity
     */
    int getCapacity();

    /**
     * Returns the current size of the team.
     *
     * @return the current size
     */
    int getCurrentSize();

    /**
     * Returns a set which includes all current players in the team
     *
     * @return the given set
     */
    Set<Player> getPlayers();
}
