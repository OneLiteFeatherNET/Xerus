package de.icevizion.xerus.api.team;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author theEvilReaper
 * @since 1.0.0
 * @version 1.0.3
 */
@ApiStatus.NonExtendable
public final class TeamServiceImpl<T extends Team> implements TeamService<T> {

    private final List<T> teams;

    /**
     * Creates a new instance from the {@link TeamServiceImpl}.
     */
    public TeamServiceImpl() {
        this.teams = new ArrayList<>();
    }

    /**
     * Creates a new instance from the {@link TeamServiceImpl} with the given parameter.
     * @param capacity The size of the underlying list
     */
    public TeamServiceImpl(int capacity) {
        this.teams = new ArrayList<>(capacity);
    }

    /**
     * Add a team to the service.
     * @param team The team to add
     */
    @Override
    public void add(@NotNull T team) {
        this.teams.add(team);
    }

    /**
     * Remove a team from the service.
     * @param team The team to remove
     */
    @Override
    public void remove(@NotNull T team) {
        this.teams.remove(team);
    }

    /**
     * Removes a team by his identifier.
     * @param identifier the identifier from the team
     */
    @Override
    public void remove(@NotNull String identifier) {
        this.teams.removeIf(team -> team.getName().equals(identifier) || team.getIdentifier().equals(identifier));
    }

    /**
     * Clears the underlying team list.
     * All players in the teams will be removed
     */
    @Override
    public void clear() {
        if (this.teams.isEmpty()) return;

        for (int i = 0; i < this.teams.size(); i++) {
            this.teams.get(i).clearPlayers();
        }

        this.teams.clear();
    }

    /**
     * Returns the team based on the specified name.
     * @param identifier of the team
     * @return The team in an {@link Optional}
     */
    @Override
    public Optional<@Nullable T> getTeam(@NotNull String identifier) {
        int i = 0;

        while (i < teams.size() && !teams.get(i).getIdentifier().equals(identifier)) {
            i++;
        }

        return Optional.ofNullable(teams.get(i));
    }

    /**
     * Returns if a team exists that matches wit the given name.
     * @param identifier The name from the team
     * @return True when the team exists otherwise false
     */
    @Override
    public boolean exists(@NotNull String identifier) {
        if (teams.isEmpty()) {
            return false;
        }

        int i = 0;

        while (i < teams.size() && !teams.get(i).getIdentifier().equals(identifier)) {
            i++;
        }

        return i != teams.size();
    }

    /**
     * Returns the team based on the given player.
     * @param player The player from which the team is determined
     * @return The team in an {@link Optional}
     */
    @Override
    public Optional<@Nullable T> getTeam(@NotNull Player player) {
        T team = null;

        for (int i = 0; i < getTeams().size() && team == null; i++) {
            if (getTeams().get(i).hasPlayer(player)) {
                team = getTeams().get(i);
            }
        }

        return Optional.ofNullable(team);
    }

    /**
     * Returns the team with the fewest players.
     * @return the smallest team
     */
    @Override
    public Optional<@Nullable T> getSmallestTeam() {
        if (!teams.isEmpty()) {
            int i = 1;
            var team = teams.getFirst();

            while (i < teams.size() && teams.get(i).getCurrentSize() < team.getCurrentSize()) {
                team = teams.get(i);
                i++;
            }

            return Optional.ofNullable(team);
        }

        return Optional.empty();
    }

    /**
     * Returns a list with all current available teams.
     * @return The underlying list
     */
    @Override
    public @NotNull List<T> getTeams() {
        return teams;
    }
}