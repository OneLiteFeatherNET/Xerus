package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.Joinable;
import de.icevizion.xerus.api.ColorData;
import de.icevizion.xerus.api.team.event.MultiPlayerTeamEvent;
import de.icevizion.xerus.api.team.event.PlayerTeamEvent;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventDispatcher;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.UnknownNullability;

/**
 * The Team interface represents a blueprint for managing a team or a similar group in a software application.
 * It defines essential methods for organizing and interacting with a team of players or members.
 * @author theEvilReaper
 * @version 1.0.3
 * @since 1.1.6
 **/
public interface Team extends Joinable {

    Runnable EMPTY = () -> { };

    /**
     * Creates a new instance from the {@link TeamImpl.Builder} interface.
     * @return the created instance
     */
    @Contract(value = " -> new", pure = true)
    static @NotNull Builder builder() {
        return new TeamBuilder(null);
    }

    /**
     * Creates a new instance from a {@link Team.Builder} to create a new team.
     * This method allows to set a {@link TeamCreator} to create custom teams over the builder
     * @param creator the creator to use
     * @return the builder instance
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull Builder builder(@NotNull TeamCreator creator) {
        return new TeamBuilder(creator);
    }

    /**
     * Creates a new instance from the {@link Builder} to create a team.
     * @param name the name of the team
     * @param colorData the {@link ColorData} to set
     * @return the created instance
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull Team of(@NotNull String name, @NotNull ColorData colorData) {
        return builder().name(name).colorData(colorData).build();
    }

    /**
     * Creates a new instance from the {@link Builder} to create a team.
     * @param name the name of the team
     * @param colorData the {@link ColorData} to set
     * @param capacity the capacity of the team
     * @return the created instance
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static @NotNull Team of(@NotNull String name, @NotNull ColorData colorData, int capacity) {
        return builder().name(name).colorData(colorData).capacity(capacity).build();
    }

    /**
     * Add a single {@link Player} entry to a structure.
     * @param player the player to add
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void addPlayer(@NotNull Player player, @Nullable Consumer<Player> consumer) {
        if (getPlayers().add(player)) {
            var teamEvent = PlayerTeamEvent.addEvent(player, this);
            EventDispatcher.callCancellable(teamEvent, consumer != null ? () -> consumer.accept(player) : EMPTY);
        }
    }

    /**
     * Add certain players to a team.
     * @param players the set with the players to add
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void addPlayers(@NotNull Set<Player> players, @Nullable Consumer<Player> consumer) {
        if (players.isEmpty()) return;
        players.removeIf(player -> !getPlayers().add(player));
        Runnable successCallback = consumer == null ? EMPTY : () -> getPlayers().forEach(consumer);
        EventDispatcher.callCancellable(MultiPlayerTeamEvent.addEvent(this, players), successCallback);
    }

    /**
     * Remove a single {@link Player} entry from a structure.
     * @param paramPlayer the player to remove
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void removePlayer(@NotNull Player paramPlayer, @Nullable Consumer<Player> consumer) {
        if (getPlayers().remove(paramPlayer)) {
            var event = PlayerTeamEvent.removeEvent(paramPlayer, this);
            EventDispatcher.callCancellable(event, consumer == null ? EMPTY : () -> consumer.accept(paramPlayer));
        }
    }

    /**
     * Remove certain players from a team.
     * @param players The set of players to remove
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void removePlayers(@NotNull Set<Player> players, @Nullable Consumer<Player> consumer) {
        if (players.isEmpty()) return;
        players.removeIf(player -> getPlayers().contains(player));
        Runnable successCallback = consumer == null ? EMPTY : () -> getPlayers().forEach(consumer);
        EventDispatcher.callCancellable(MultiPlayerTeamEvent.removeEvent(this, players), successCallback);
    }

    /**
     * Set / overwrite the capacity of the team.
     * @param capacity The capacity to set
     */
    void setCapacity(int capacity);

    /**
     * Checks if a player can join a team or not.
     * @return true when a player can join otherwise false
     */
    boolean canJoin();

    /**
     * Checks if a given player is in the team.
     * @param player The player to check
     * @return True when the player is in the team otherwise false
     */
    default boolean hasPlayer(@NotNull Player player) {
        return getPlayers().contains(player);
    }

    /**
     * Send a specific message to each player in the team.
     * @param component The message wraps as {@link Component} who should send
     */
    default void sendMessage(@NotNull Component component) {
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
        removePlayers(getPlayers());
    }

    /**
     * Returns the identifier of the team.
     * @return the underlying value
     */
    @NotNull String getIdentifier();

    /**
     * Returns the name of the team.
     * @return The name of the team
     */
    @UnknownNullability String getName(Locale locale);

    /**
     * Returns the name of the team.
     * @return The name of the team
     */
    default @NotNull String getName() {
        return getName(null);
    }

    /**
     * Returns the name with the color.
     * @return The name with the color
     */
    @UnknownNullability String getColoredName(Locale locale);

    /**
     * Returns the name with the color.
     * @return The name with the color
     */
    default @NotNull String getColoredName() {
        return getColoredName(null);
    }

    /**
     * Returns the given color data from the team.
     * @return the color data
     */
    @NotNull ColorData getColorData();

    /**
     * Returns a boolean indicator if the team contains entries or not.
     * @return true when the team has no entries otherwise false
     */
    default boolean isEmpty() {
        return this.getPlayers().isEmpty();
    }

    /**
     * Returns the maximum capacity of the team.
     * @return -1 when no capacity is set otherwise the capacity
     */
    int getCapacity();

    /**
     * Returns the current size of the team.
     * @return the current size
     */
    int getCurrentSize();

    /**
     * Returns a set which includes all current players in the team
     * @return the given set
     */
    @NotNull Set<Player> getPlayers();

    /**
     * The interface defines all relevant method for a builder pattern.
     */
    sealed interface Builder permits TeamBuilder {

        /**
         * Set a name to the team.
         * @param name the new name set
         * @return the builder instance
         */
        @NotNull Builder name(@NotNull String name);

        /**
         * Set the used color to the team
         * @param colorData the {@link ColorData} to set
         * @return the builder instance
         */
        @NotNull Builder colorData(@NotNull ColorData colorData);

        /**
         * Set the maximum size for the team.
         * @param capacity the capacity to set
         * @return the builder instance
         */
        @NotNull Builder capacity(int capacity);

        /**
         * Creates a new instance from the team with the given values from the builder.
         * When some value is invalid the creation process will fail with an exception
         * @return the builder instance
         */
        @NotNull Team build();
    }
}