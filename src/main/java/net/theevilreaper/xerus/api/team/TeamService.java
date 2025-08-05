package net.theevilreaper.xerus.api.team;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * The {link TeamService} interface is the ground definition for a service which handles teams in different cases.
 * It contains management methods like add, remove or other methods to retrieve teams.
 *
 * @author theEvilReaper
 * @version 1.0.4
 * @since 1.1.0
 **/
public interface TeamService {

    /**
     * Add a team to the service.
     *
     * @param team which should be added
     */
    void add(@NotNull Team team);

    /**
     * Remove a team from the service.
     *
     * @param team which should be removed
     */
    void remove(@NotNull Team team);

    /**
     * Removes the team by his given identifier.
     * The identifier is the name of the team or the translated key
     *
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
     *
     * @param identifier The identifier from the team
     * @return true when the team exists otherwise false
     */
    boolean exists(@NotNull String identifier);

    /**
     * Returns the team based on the specified identifier.
     *
     * @param identifier of the team
     * @return the team in an {@link Optional}
     */
    Optional<@Nullable Team> getTeam(@NotNull String identifier);

    /**
     * Returns the team based on the given player.
     *
     * @param player The player from which the team is determined
     * @return the team in an {@link Optional}
     */
    Optional<@Nullable Team> getTeam(@NotNull Player player);

    /**
     * Returns the team with the fewest players.
     *
     * @return the smallest team
     */
    Optional<@Nullable Team> getSmallestTeam();

    /**
     * Returns an indication of whether the service has teams.
     *
     * @return true if the service has teams otherwise false
     */
    default boolean hasTeams() {
        return !getTeams().isEmpty();
    }

    /**
     * Returns a list with all current available teams.
     *
     * @return the underlying list
     */
    @NotNull List<Team> getTeams();
}