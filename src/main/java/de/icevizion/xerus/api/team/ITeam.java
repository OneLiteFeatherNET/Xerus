package de.icevizion.xerus.api.team;

import de.icevizion.aves.item.IItem;
import de.icevizion.xerus.api.Joinable;
import de.icevizion.xerus.api.ColorData;
import de.icevizion.xerus.api.team.event.MultiPlayerTeamEvent;
import de.icevizion.xerus.api.team.event.TeamAction;
import de.icevizion.xerus.api.team.event.PlayerTeamEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Set;

/**
 * @author theEvilReaper
 * @version 1.0.2
 * @since 1.1.6
 **/
public interface ITeam extends Joinable {

    /**
     * Add specific player to a specific team
     * @param paramPlayer the player to add
     */
    @Override
    default void addPlayer(@NotNull Player paramPlayer) {
        if (getPlayers().add(paramPlayer)) {
            MinecraftServer.getGlobalEventHandler().call(new PlayerTeamEvent<>(
                    paramPlayer, this, TeamAction.ADD));
        }
    }

    /**
     * Add certain players to a team.
     * @param players the set with the players to add
     */
    @Override
    default void addPlayers(@NotNull Set<Player> players) {
        if (players.isEmpty()) return;

        players.removeIf(player -> !getPlayers().add(player));

        MinecraftServer.getGlobalEventHandler().call(new MultiPlayerTeamEvent<>(
                this, players, TeamAction.ADD));
    }

    /**
     * Remove specific player from a team.
     * @param paramPlayer the player to remove
     */
    @Override
    default void removePlayer(@NotNull Player paramPlayer) {
        if (getPlayers().remove(paramPlayer)) {
            MinecraftServer.getGlobalEventHandler().call(new PlayerTeamEvent<>(
                    paramPlayer, this, TeamAction.REMOVE));
        }
    }

    /**
     * Remove certain players from a team.
     * @param players The set of players to remove
     */
    @Override
    default void removePlayers(@NotNull Set<Player> players) {
        if (players.isEmpty()) return;

        players.removeIf(player -> !getPlayers().remove(player));

        MinecraftServer.getGlobalEventHandler().call(new MultiPlayerTeamEvent<>(
                this, players, TeamAction.ADD));
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
     * @param message The message who should send
     */
    default void sendMessage(@NotNull String message) {
        if (message.trim().isEmpty()) return;
        for (Player player : getPlayers()) {
            player.sendMessage(message);
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
    @NotNull
    String getIdentifier();

    /**
     * Returns the name of the team.
     * @return The name of the team
     */
    String getName(Locale locale);

    /**
     * Returns the name of the team.
     * @return The name of the team
     */
    default String getName() {
        return getName(null);
    }

    /**
     * Returns the name with the color.
     * @return The name with the color
     */
    String getColoredName(Locale locale);

    /**
     * Returns the name with the color.
     * @return The name with the color
     */
    default String getColoredName() {
        return getColoredName(null);
    }

    /**
     * Returns the given color data from the team.
     * @return the color data
     */
    @NotNull
    ColorData getColorData();

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
     * Returns the icon from the team.
     * @return the underlying icon
     */
    @Nullable
    IItem getIcon();

    /**
     * Returns a set which includes all current players in the team
     * @return the given set
     */
    @NotNull
    Set<Player> getPlayers();

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
         * Set the icon to the team.
         * @param icon the icon to set
         * @return the builder instance
         */
        @NotNull Builder icon(@NotNull IItem icon);

        /**
         * Set the maximum size for the team.
         * @param capacity the capacity to set
         * @return the builder instance
         */
        @NotNull Builder capacity(int capacity);

        /**
         * Creates a new instance from the team with the given values from the builder.
         * When some value is invalid the creation process will fail with an exception
         * @return the builder instace
         */
        @NotNull Team build();

    }
}