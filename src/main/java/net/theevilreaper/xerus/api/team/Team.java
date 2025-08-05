package net.theevilreaper.xerus.api.team;

import net.theevilreaper.xerus.api.Joinable;
import net.theevilreaper.xerus.api.ColorData;
import net.theevilreaper.xerus.api.team.event.MultiPlayerTeamEvent;
import net.theevilreaper.xerus.api.team.event.PlayerTeamEvent;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventDispatcher;
import net.theevilreaper.xerus.api.team.event.TeamAction;
import org.jetbrains.annotations.*;

import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The Team interface represents a blueprint for managing a team or a similar group in a software application.
 * It defines essential methods for organizing and interacting with a team of players or members.
 *
 * @author theEvilReaper
 * @version 1.0.4
 * @since 1.1.6
 **/
public interface Team extends Joinable {

    /**
     * An empty runnable implementation.
     */
    @NotNull Runnable EMPTY = () -> {
    };

    /**
     * Creates a new instance from the {@link TeamImpl.Builder} interface.
     *
     * @return the created instance
     */
    @Contract(value = " -> new", pure = true)
    static @NotNull Builder builder() {
        return new TeamBuilder();
    }

    /**
     * Creates a new instance from a {@link Team.Builder} to create a new team.
     * This method allows to set a {@link TeamCreator} to create custom teams over the builder
     *
     * @param creator the creator to use
     * @return the builder instance
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull Builder builder(@NotNull TeamCreator creator) {
        return new TeamBuilder(creator);
    }

    /**
     * Creates a new instance from the {@link Builder} to create a team.
     *
     * @param name      the name of the team
     * @param colorData the {@link ColorData} to set
     * @return the created instance
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull Team of(@NotNull String name, @NotNull ColorData colorData) {
        return builder().name(name).colorData(colorData).build();
    }

    /**
     * Creates a new instance from the {@link Builder} to create a team.
     *
     * @param name      the name of the team
     * @param colorData the {@link ColorData} to set
     * @param capacity  the capacity of the team
     * @return the created instance
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static @NotNull Team of(@NotNull String name, @NotNull ColorData colorData, int capacity) {
        return builder().name(name).colorData(colorData).capacity(capacity).build();
    }

    /**
     * Add a single {@link Player} entry to a structure.
     *
     * @param player   the player to add
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void addPlayer(@NotNull Player player, @Nullable Consumer<Player> consumer) {
        if (getPlayers().add(player)) {
            PlayerTeamEvent teamEvent = new PlayerTeamEvent(player, this, TeamAction.ADD);
            EventDispatcher.callCancellable(teamEvent, consumer != null ? () -> consumer.accept(player) : EMPTY);
        }
    }

    /**
     * Add certain players to a team.
     *
     * @param players  the set with the players to add
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void addPlayers(@NotNull Set<Player> players, @Nullable Consumer<Player> consumer) {
        if (players.isEmpty()) return;
        players.removeIf(player -> !getPlayers().add(player));
        Runnable successCallback = consumer == null ? EMPTY : () -> getPlayers().forEach(consumer);
        EventDispatcher.callCancellable(new MultiPlayerTeamEvent(this, players, TeamAction.ADD), successCallback);
    }

    /**
     * Remove a single {@link Player} entry from a structure.
     *
     * @param paramPlayer the player to remove
     * @param consumer    a consumer which is called to execute some logic
     */
    @Override
    default void removePlayer(@NotNull Player paramPlayer, @Nullable Consumer<Player> consumer) {
        if (getPlayers().remove(paramPlayer)) {
            PlayerTeamEvent teamEvent = new PlayerTeamEvent(paramPlayer, this, TeamAction.REMOVE);
            EventDispatcher.callCancellable(teamEvent, consumer == null ? EMPTY : () -> consumer.accept(paramPlayer));
        }
    }

    /**
     * Remove certain players from a team.
     *
     * @param players  The set of players to remove
     * @param consumer a consumer which is called to execute some logic
     */
    @Override
    default void removePlayers(@NotNull Set<Player> players, @Nullable Consumer<Player> consumer) {
        if (players.isEmpty()) return;
        players.removeIf(player -> getPlayers().contains(player));
        Runnable successCallback = consumer == null ? EMPTY : () -> getPlayers().forEach(consumer);
        EventDispatcher.callCancellable(new MultiPlayerTeamEvent(this, players, TeamAction.REMOVE), successCallback);
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
    default boolean hasPlayer(@NotNull Player player) {
        return getPlayers().contains(player);
    }

    /**
     * Send a specific message to each player in the team.
     *
     * @param component the message wraps as {@link Component} who should send
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
     *
     * @return the underlying value
     */
    @NotNull String getIdentifier();

    /**
     * Return the name of the team based on the given locale.
     *
     * @param locale the locale to use for the name
     * @return the name of the team
     */
    @UnknownNullability
    Component getName(Locale locale);

    /**
     * Returns the name of a team as a {@link Component}.
     *
     * @return the given name
     */
    default @NotNull Component getName() {
        return getName(null);
    }

    /**
     * Returns a colored name based on the given locale.
     *
     * @param locale the locale to use for the name
     * @return the colored name
     */
    @UnknownNullability
    Component getColoredName(Locale locale);

    /**
     * Returns the name with the color.
     *
     * @return the colored name
     */
    default @NotNull Component getColoredName() {
        return getColoredName(null);
    }

    /**
     * Returns the given color data from the team.
     *
     * @return the color data
     */
    @NotNull ColorData getColorData();

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
    @NotNull Set<Player> getPlayers();

    /**
     * The interface defines all relevant methods for a builder pattern.
     */
    @ApiStatus.NonExtendable
    sealed interface Builder permits TeamBuilder {

        /**
         * Set a name to the team.
         *
         * @param name the new name set
         * @return the builder instance
         */
        @NotNull Builder name(@NotNull String name);

        /**
         * Set the used color to the team
         *
         * @param colorData the {@link ColorData} to set
         * @return the builder instance
         */
        @NotNull Builder colorData(@NotNull ColorData colorData);

        /**
         * Set the maximum size for the team.
         *
         * @param capacity the capacity to set
         * @return the builder instance
         */
        @NotNull Builder capacity(int capacity);

        /**
         * Creates a new instance from the team with the given values from the builder.
         * When some value is invalid the creation process will fail with an exception
         *
         * @return the builder instance
         */
        @NotNull Team build();
    }
}