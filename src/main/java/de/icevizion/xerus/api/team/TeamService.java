package de.icevizion.xerus.api.team;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * The class includes all relevant method for a team service.
 * @author theEvilReaper
 * @version 1.0.3
 * @since 1.0.0
 **/
public interface TeamService<TEAM extends Team> {

    /**
     * Add a team to the service.
     * @param team The team to add
     */
    void add(@NotNull TEAM team);

    /**
     * Remove a team from the service.
     * @param team The team to remove
     */
    void remove(@NotNull TEAM team);

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
    Optional<TEAM> getTeam(@NotNull String identifier);

    /**
     * Returns the team based on the given player.
     * @param player The player from which the team is determined
     * @return The team in an {@link Optional}
     */
    Optional<TEAM> getTeam(@NotNull Player player);

    /**
     * Returns the team with the fewest players.
     * @return the smallest team
     */
    Optional<TEAM> getSmallestTeam();

    /**
     * Returns a list with all current available teams.
     * @return The underlying list
     */
    List<TEAM> getTeams();
}