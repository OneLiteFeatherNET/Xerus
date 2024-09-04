package de.icevizion.xerus.api.team;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * The class includes all relevant method for a team service.
 * @author theEvilReaper
 * @version 1.0.4
 * @since 1.0.0
 **/
public interface TeamService<T extends Team> {

    /**
     * Add a team to the service.
     * @param t The team to add
     */
    void add(@NotNull T t);

    /**
     * Remove a team from the service.
     * @param t The team to remove
     */
    void remove(@NotNull T t);

    /**
     * Removes the team by his given identifier.
     * The identifier is the name of the team or the translated key
     * @param identifier the identifier from the team
     */
    void remove(@NotNull String identifier);

    /**
     * Clears the underlying team list.
     * All players in the teams will be removed
     */
    void clear();

    /**
     * Returns if a team exists that matches wit the given identifier.
     * @param identifier The identifier from the team
     * @return True when the team exists otherwise false
     */
    boolean exists(@NotNull String identifier);

    /**
     * Returns the team based on the specified identifier.
     * @param identifier of the team
     * @return The team in an {@link Optional}
     */
    Optional<@Nullable T> getTeam(@NotNull String identifier);

    /**
     * Returns the team based on the given player.
     * @param player The player from which the team is determined
     * @return The team in an {@link Optional}
     */
    Optional<@Nullable T> getTeam(@NotNull Player player);

    /**
     * Returns the team with the fewest players.
     * @return the smallest team
     */
    Optional<@Nullable T> getSmallestTeam();

    /**
     * Returns an indication of whether the service has teams.
     * @return true if the service has teams otherwise false
     */
    default boolean hasTeams() {
        return !getTeams().isEmpty();
    }

    /**
     * Returns a list with all current available teams.
     * @return The underlying list
     */
    @NotNull List<T> getTeams();
}